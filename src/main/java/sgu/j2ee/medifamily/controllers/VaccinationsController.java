package sgu.j2ee.medifamily.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.VaccinationDto;
import sgu.j2ee.medifamily.dtos.VaccinationFilterDTO;
import sgu.j2ee.medifamily.services.VaccinationService;

@RestController
@RequestMapping("/api/vaccinations")
@RequiredArgsConstructor
public class VaccinationsController {

	private final VaccinationService vaccinationService;

	@GetMapping
	public PagedModel<VaccinationDto> searchVaccinations(
			VaccinationFilterDTO filter,
			@PageableDefault(size = 20) @ParameterObject Pageable pageable) {
		Page<VaccinationDto> page = vaccinationService.filterVaccinations(filter, pageable);
		return new PagedModel<>(page);
	}

	@PostMapping
	public VaccinationDto createVaccination(@RequestBody VaccinationDto vaccination) {
		return vaccinationService.createVaccination(vaccination);
	}

	@GetMapping("/{id}")
	public VaccinationDto getVaccination(@PathVariable Long id) {
		return vaccinationService.getVaccination(id);
	}

	@PatchMapping("/{id}")
	public VaccinationDto updateVaccination(@PathVariable Long id, @RequestBody VaccinationDto vaccination) {
		return vaccinationService.updateVaccination(id, vaccination);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVaccination(@PathVariable Long id) {
		vaccinationService.deleteVaccination(id);
		return ResponseEntity.noContent().build();
	}
}
