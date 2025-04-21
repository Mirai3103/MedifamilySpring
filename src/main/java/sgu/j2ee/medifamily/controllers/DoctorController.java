package sgu.j2ee.medifamily.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.DoctorSearchRequest;
import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.services.DoctorService;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {
	private final DoctorService doctorService;

	@GetMapping("/search")
	public PagedModel<DoctorDTO> searchDoctors(
			@RequestParam DoctorSearchRequest doctorSearchRequest,
			@ParameterObject Pageable pageable

	) {

		var res = doctorService.searchWithFilter(doctorSearchRequest, pageable);
		return new PagedModel<>(res);
	}

	@GetMapping("/{id}")
	public DoctorDTO getDoctor(@PathVariable Long id) {
		return doctorService.getOne(id);
	}

	@PostMapping
	public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
		DoctorDTO created = doctorService.create(doctorDTO);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	@PatchMapping("/{id}")
	public DoctorDTO updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
		return doctorService.updatePartial(id, doctorDTO);
	}
}
