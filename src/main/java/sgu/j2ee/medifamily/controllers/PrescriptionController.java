package sgu.j2ee.medifamily.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.PrescriptionDto;
import sgu.j2ee.medifamily.mappers.IPrescriptionItemMapper;
import sgu.j2ee.medifamily.mappers.IPrescriptionMapper;
import sgu.j2ee.medifamily.services.PrescriptionService;

@RestController

@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
	private final IPrescriptionItemMapper prescriptionItemMapper;
	private final IPrescriptionMapper prescriptionMapper;
	private final PrescriptionService prescriptionService;

	@PostMapping()
	public ResponseEntity<Void> createPrescription(@Valid PrescriptionDto prescriptionDto) {
		var prescription = prescriptionMapper.toEntity(prescriptionDto);
		prescriptionService.createPrescription(prescription);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePrescription(Long id) {
		prescriptionService.deletePrescription(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{id}")
	public ResponseEntity<PrescriptionDto> getPrescription(@PathVariable Long id) {
		var prescription = prescriptionService.findById(id);
		return ResponseEntity.ok(prescriptionMapper.toDto(prescription));
	}

}
