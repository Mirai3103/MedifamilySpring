package sgu.j2ee.medifamily.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.Family;
import sgu.j2ee.medifamily.entities.FamilyDoctor;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.DoctorRepository;
import sgu.j2ee.medifamily.repositories.FamilyDoctorRepository;
import sgu.j2ee.medifamily.repositories.FamilyRepository;

@Service
@RequiredArgsConstructor
public class DoctorFamilyService {

	private final FamilyDoctorRepository familyDoctorRepository;
	private final DoctorRepository doctorRepository;
	private final FamilyRepository familyRepository;

	// Gửi yêu cầu bác sĩ gia đình (status = PENDING)
	public FamilyDoctor sendRequest(Long familyId, Long doctorId, String notes) {
		Family family = familyRepository.findById(familyId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy gia đình"));

		Doctor doctor = doctorRepository.findById(doctorId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy bác sĩ"));

		FamilyDoctor entity = FamilyDoctor.builder()
				.family(family)
				.doctor(doctor)
				.status(FamilyDoctor.Status.PENDING)
				.notes(notes)
				.createdAt(LocalDateTime.now())
				.updatedAt(LocalDateTime.now())
				.build();

		return familyDoctorRepository.save(entity);
	}

	// Bác sĩ chấp nhận yêu cầu
	public FamilyDoctor acceptRequest(Long requestId) {
		FamilyDoctor request = familyDoctorRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu"));

		request.setStatus(FamilyDoctor.Status.ACCEPTED);
		request.setUpdatedAt(LocalDateTime.now());

		return familyDoctorRepository.save(request);
	}

	// Bác sĩ từ chối yêu cầu
	public FamilyDoctor rejectRequest(Long requestId, String note) {
		FamilyDoctor request = familyDoctorRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu"));

		request.setStatus(FamilyDoctor.Status.REJECTED);
		request.setNotes(note);
		request.setUpdatedAt(LocalDateTime.now());

		return familyDoctorRepository.save(request);
	}

	// Gia đình chuyển bác sĩ (thay đổi doctor_id, giữ lại history nếu cần)
	public FamilyDoctor changeDoctor(Long requestId, Long newDoctorId) {
		FamilyDoctor request = familyDoctorRepository.findById(requestId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu"));

		Doctor newDoctor = doctorRepository.findById(newDoctorId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy bác sĩ mới"));

		request.setDoctor(newDoctor);
		request.setStatus(FamilyDoctor.Status.PENDING);
		request.setUpdatedAt(LocalDateTime.now());

		return familyDoctorRepository.save(request);
	}

	// Danh sách gia đình của bác sĩ (theo id bác sĩ, filter theo status, có sort và
	// phân trang)
	public Page<FamilyDoctor> getFamiliesByDoctor(Long doctorId, FamilyDoctor.Status status, Pageable pageable) {
		return familyDoctorRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("doctor").get("id"), doctorId));
			if (status != null) {
				predicates.add(cb.equal(root.get("status"), status));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		}, pageable);
	}

	// Danh sách bác sĩ của gia đình (theo id family, filter theo status, có sort và
	// phân trang)
	public FamilyDoctor getDoctorsByFamily(Long familyId) {
		// tạm thời 1 gia đình chỉ định 1 bác sĩ, sau này có thể thay đổi sau
		return familyDoctorRepository.findFirstByFamilyId(familyId)
				.orElse(null);
	}

	public void deleteRequest(Long id) {
		FamilyDoctor request = familyDoctorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu"));

		familyDoctorRepository.delete(request);
	}
}
