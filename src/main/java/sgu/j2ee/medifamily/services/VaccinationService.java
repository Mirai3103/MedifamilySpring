package sgu.j2ee.medifamily.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.VaccinationDto;
import sgu.j2ee.medifamily.dtos.VaccinationFilterDTO;
import sgu.j2ee.medifamily.dtos.VaccinationSpecs;
import sgu.j2ee.medifamily.entities.Notification;
import sgu.j2ee.medifamily.entities.Vaccination;
import sgu.j2ee.medifamily.mappers.VaccinationMapper;
import sgu.j2ee.medifamily.repositories.*;

@Service
@RequiredArgsConstructor
public class VaccinationService {
	private final VaccinationRepository vaccinationRepository;
	private final VaccinationMapper vaccinationMapper;
	private final FamilyRepository familyRepository;
	private final ProfileRepository profileRepository;
	private final VaccinationMapper mapper;
	private final JavaMailSender mailSender;
	private final NotificationRepository notificationRepository;

	public Page<VaccinationDto> filterVaccinations(VaccinationFilterDTO filter, Pageable pageable) {
		Specification<Vaccination> spec = Specification.where(null);

		if (filter.getProfileId() != null) {
			spec = spec.and(VaccinationSpecs.byProfileId(filter.getProfileId()));
		}

		if (filter.getKeyword() != null && !filter.getKeyword().isBlank()) {
			spec = spec.and(VaccinationSpecs.keywordLike(filter.getKeyword()));
		}

		if (filter.getFromDate() != null && filter.getToDate() != null) {
			spec = spec.and(VaccinationSpecs.vaccinationBetween(filter.getFromDate(), filter.getToDate()));
		}

		// xử lý sort
		Sort sort = Sort.by(Sort.Direction.fromString(filter.getSortDirection()), filter.getSortBy());
		Pageable finalPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
		var page = vaccinationRepository.findAll(spec, finalPageable);
		return page.map(vaccinationMapper::toDto);
	}

	public VaccinationDto getVaccination(Long id) {
		return vaccinationRepository.findById(id)
				.map(vaccinationMapper::toDto)
				.orElse(null);
	}

	public VaccinationDto createVaccination(VaccinationDto vaccination) {
		var entity = vaccinationMapper.toEntity(vaccination);
		var profile = profileRepository.findById(vaccination.getProfileId());
		if (profile.isEmpty()) {
			return null;
		}
		entity.setProfile(profile.get());
		return vaccinationMapper.toDto(vaccinationRepository.save(entity));
	}

	public VaccinationDto updateVaccination(Long id, VaccinationDto vaccination) {
		var existingVaccination = vaccinationRepository.findById(id);
		if (existingVaccination.isEmpty()) {
			return null;
		}
		vaccinationMapper.partialUpdate(vaccination, existingVaccination.get());
		return vaccinationMapper.toDto(vaccinationRepository.save(existingVaccination.get()));
	}

	@Transactional

	public void deleteVaccination(Long id) {
		var vaccination = vaccinationRepository.findById(id);
		if (vaccination.isEmpty()) {
			return;
		}
		vaccinationRepository.delete(vaccination.get());
	}

	public List<Vaccination> getTomorrowVaccinations() {
		LocalDateTime start = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime end = start.plusDays(1);
		return vaccinationRepository.findVaccinationsByDateRange(start, end);
	}

	public void sendReminders() {
		List<Vaccination> list = getTomorrowVaccinations();

		for (Vaccination v : list) {

			String to = v.getProfile().getEmail(); // cần đảm bảo `Profile` có field `email`
			if (to == null)
				continue;

			String subject = "Nhắc nhở lịch tiêm ngừa";
			String content = String.format(
					"Xin chào,\n\nBạn có lịch tiêm '%s' vào ngày mai (%s) tại %s.\n\nVui lòng đến đúng giờ.\n\nMediFamily.",
					v.getVaccineName(),
					v.getVaccinationDate().toLocalDate(),
					v.getLocation());
			Notification notification = new Notification();
			notification.setUser(v.getProfile().getUser());
			notification.setTitle(subject);
			notification.setContent(content);
			notification.setReferenceType("Vaccination");
			notification.setReferenceId(v.getId().toString());
			notification.setIsRead(false);
			notificationRepository.save(notification);
			// toDo: send email
			// sendEmail(to, subject, content);
		}
	}

	private void sendEmail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		mailSender.send(message);
	}

}
