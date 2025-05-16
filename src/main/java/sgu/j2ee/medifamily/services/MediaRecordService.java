package sgu.j2ee.medifamily.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.MedicalRecord;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.MedicalRecordRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class MediaRecordService {
	private final MedicalRecordRepository medicalRecordRepository;
	private final ProfileRepository profileRepository;
	private final FileService fileService;

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
}
