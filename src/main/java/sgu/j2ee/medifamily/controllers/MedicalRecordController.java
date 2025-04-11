package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.MedicalRecordDto;
import sgu.j2ee.medifamily.dtos.PrescriptionDto;
import sgu.j2ee.medifamily.mappers.IMediaRecordMapper;
import sgu.j2ee.medifamily.mappers.IPrescriptionMapper;
import sgu.j2ee.medifamily.services.MediaRecordService;
import sgu.j2ee.medifamily.services.PrescriptionService;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
	private final MediaRecordService mediaRecordService;
	private final IMediaRecordMapper mediaRecordMapper;
	private final IPrescriptionMapper prescriptionMapper;
	private final PrescriptionService prescriptionService;

	@GetMapping("profile/{profileId}")
	public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecords(@PathVariable Long profileId) {
		return ResponseEntity.ok(
				mediaRecordMapper.toDTO(mediaRecordService.getAllMedicalRecordsByProfileId(profileId)));
	}

	@PostMapping("")
	public ResponseEntity<Void> createMedicalRecord(
			@RequestBody MedicalRecordDto medicalRecordDto) {
		var medicalRecord = mediaRecordMapper.toEntity(medicalRecordDto);
		mediaRecordService.createMedicalRecord(medicalRecord);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMedicalRecord(@PathVariable Long id) {
		mediaRecordService.deleteMedicalRecord(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicalRecordDto> getMedicalRecord(@PathVariable Long id) {
		var medicalRecord = mediaRecordService.getById(id);
		return ResponseEntity.ok(mediaRecordMapper.toDTO(medicalRecord));
	}

	@GetMapping("{id}/prescriptions")
	public ResponseEntity<List<PrescriptionDto>> getPrescriptions(@PathVariable Long id) {
		var prescriptions = prescriptionService.findAllByMedicalRecordId(id);
		return ResponseEntity.ok(prescriptionMapper.toDto(prescriptions));
	}
}
