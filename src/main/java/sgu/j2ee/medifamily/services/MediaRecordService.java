package sgu.j2ee.medifamily.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.MedicalRecord;
import sgu.j2ee.medifamily.entities.Notification;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.MedicalRecordRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class MediaRecordService {
	private final MedicalRecordRepository medicalRecordRepository;
	private final ProfileRepository profileRepository;
	private final FileService fileService;
	private final JavaMailSender mailSender;

	public List<MedicalRecord> getAllMedicalRecordsByProfileId(Long profileId) {
		return medicalRecordRepository.findAllByProfileIdOrderByCreatedAtDesc(profileId);
	}

	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		var profile = profileRepository.findById(medicalRecord.getProfileId());
		if (profile.isEmpty()) {
			throw new NotFoundException("Profile not found");
		}
		medicalRecord.setProfile(profile.get());
		return medicalRecordRepository.save(medicalRecord);
	}

	public MedicalRecord getMedicalRecordById(Long id) {
		return medicalRecordRepository.findById(id).orElse(null);
	}

	public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecord) {
		MedicalRecord existingRecord = getMedicalRecordById(id);
		if (existingRecord != null) {

			return medicalRecordRepository.save(existingRecord);
		}
		return null;
	}

	public void deleteMedicalRecord(Long id) {
		medicalRecordRepository.deleteById(id);
	}

	public MedicalRecord getById(Long id) {
		return medicalRecordRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Medical record not found"));
	}

	public void addAttachmentToMedicalRecord(Long medicalRecordId, List<String> attachments) {
		var medicalRecord = getMedicalRecordById(medicalRecordId);
		if (medicalRecord == null) {
			throw new NotFoundException("Medical record not found");
		}
		if (medicalRecord.getAttachments() == null) {
			medicalRecord.setAttachments(new ArrayList<>());
		}
		medicalRecord.getAttachments().addAll(attachments);
		medicalRecord.setAttachments(new ArrayList<>(medicalRecord.getAttachments()));
		medicalRecordRepository.save(medicalRecord);
	}

	public void deleteAttachmentFromMedicalRecord(Long medicalRecordId, String attachment) {
		var medicalRecord = getMedicalRecordById(medicalRecordId);
		if (medicalRecord == null) {
			throw new NotFoundException("Medical record not found");
		}
		if (medicalRecord.getAttachments() != null) {
			medicalRecord.getAttachments().remove(attachment);
			fileService.deleteFile(attachment);
		}

		medicalRecordRepository.save(medicalRecord);
	}

	public void sendFollowupReminders() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		List<MedicalRecord> records = medicalRecordRepository.findFollowupsTomorrow(tomorrow);

		for (MedicalRecord record : records) {
			Profile profile = record.getProfile();
			if (profile.getEmail() == null || profile.getEmail().isBlank())
				continue;
			Notification notification = new Notification();
			notification.setUser(record.getProfile().getUser());
			notification.setTitle(buildSubject(record));
			notification.setContent(buildContent(record));
			notification.setReferenceType("MedicalRecord");
			notification.setReferenceId(record.getId().toString());
			notification.setIsRead(false);
			// sendEmail(profile.getEmail(), buildSubject(record), buildContent(record));
		}
	}

	private void sendEmail(String to, String subject, String content) {
		var message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
	}

	private String buildSubject(MedicalRecord r) {
		return "Nhắc nhở tái khám tại " + r.getMedicalFacility();
	}

	private String buildContent(MedicalRecord r) {
		return """
				Xin chào,

				Bạn có lịch tái khám vào ngày mai (%s) tại cơ sở: %s.
				Chẩn đoán lần trước: %s
				Bác sĩ: %s

				Vui lòng sắp xếp thời gian để tái khám đúng hẹn.

				Trân trọng,
				Hệ thống MediFamily
				""".formatted(
				r.getFollowupDate(),
				r.getMedicalFacility(),
				r.getDiagnosis(),
				Optional.ofNullable(r.getDoctorName()).orElse("Không rõ"));
	}
}
