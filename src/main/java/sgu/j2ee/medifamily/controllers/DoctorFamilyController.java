package sgu.j2ee.medifamily.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.Doctor;
import sgu.j2ee.medifamily.entities.FamilyDoctor;
import sgu.j2ee.medifamily.mappers.FamilyDoctorMapper;
import sgu.j2ee.medifamily.services.DoctorFamilyService;

@RestController
@RequestMapping("/api/doctor-family")
@RequiredArgsConstructor
public class DoctorFamilyController {
	private final DoctorFamilyService doctorFamilyService;
	private final FamilyDoctorMapper familyDoctorMapper;

	@Data
	public static class DoctorFamilyRequest {
		@NotNull
		private Long doctorId;
		@NotNull
		private Long familyId;
		private String notes;
	}

	@PostMapping("")
	public FamilyDoctor sendRequest(@RequestBody DoctorFamilyRequest request) {
		return doctorFamilyService.sendRequest(request.familyId, request.doctorId, request.notes);
	}

	// Bác sĩ chấp nhận yêu cầu
	@PatchMapping("/{requestId}/accept")
	public FamilyDoctor acceptRequest(@PathVariable Long requestId) {
		return doctorFamilyService.acceptRequest(requestId);
	}

	// Bác sĩ từ chối yêu cầu
	@PatchMapping("/{requestId}/reject")
	public FamilyDoctor rejectRequest(@PathVariable Long requestId,
			@RequestParam(required = false) String note) {
		return doctorFamilyService.rejectRequest(requestId, note);
	}

	// Gia đình chuyển bác sĩ
	@PatchMapping("/{requestId}/change-doctor")
	public FamilyDoctor changeDoctor(@PathVariable Long requestId,
			@RequestParam Long newDoctorId) {
		return doctorFamilyService.changeDoctor(requestId, newDoctorId);
	}

	@GetMapping("/by-doctor")
	public PagedModel<FamilyDoctor> getFamiliesByDoctor(@RequestParam Long doctorId,
			@RequestParam(required = false) FamilyDoctor.Status status,
			@ParameterObject Pageable pageable) {
		var rs = doctorFamilyService.getFamiliesByDoctor(doctorId, status, pageable);
		return new PagedModel<>(rs);
	}

	// Lấy bác sĩ của gia đình (tạm thời 1 bác sĩ / gia đình)
	@GetMapping("/by-family")
	public Doctor getDoctorByFamily(@RequestParam Long familyId) {
		return doctorFamilyService.getDoctorsByFamily(familyId);
	}
}
