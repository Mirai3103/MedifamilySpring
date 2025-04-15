package sgu.j2ee.medifamily.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.MedicalRecordDto;
import sgu.j2ee.medifamily.dtos.PrescriptionDto;
import sgu.j2ee.medifamily.entities.FileDocument;
import sgu.j2ee.medifamily.mappers.IMediaRecordMapper;
import sgu.j2ee.medifamily.mappers.IPrescriptionMapper;
import sgu.j2ee.medifamily.services.FileService;
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
	private final FileService fileService;

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
	public ResponseEntity<PrescriptionDto> getPrescriptionByRecord(@PathVariable Long id) {
		var prescriptions = prescriptionService.findAllByMedicalRecordId(id);
		return ResponseEntity.ok(prescriptionMapper.toDto(prescriptions));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "{id}/attachments")
	public ResponseEntity<List<String>> addAttachments(@RequestParam("attachments") List<MultipartFile> attachments,
			@PathVariable String id) throws IOException {
		var attachmentsPath = fileService.uploadFile(attachments).stream().map(FileDocument::getServerPath).toList();
		mediaRecordService.addAttachmentToMedicalRecord(Long.parseLong(id), attachmentsPath);
		return ResponseEntity.ok(attachmentsPath);
	}

	@DeleteMapping(value = "{id}/attachments")
	public ResponseEntity<Void> deleteAttachment(@PathVariable String id,
			@RequestParam("attachment") String attachment) {
		mediaRecordService.deleteAttachmentFromMedicalRecord(Long.parseLong(id), attachment);
		return ResponseEntity.ok().build();
	}
}
