package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.MedicalRecordDto;
import sgu.j2ee.medifamily.mappers.IMediaRecordMapper;
import sgu.j2ee.medifamily.services.MediaRecordService;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
	private final MediaRecordService mediaRecordService;
	private final IMediaRecordMapper mediaRecordMapper;

	@GetMapping("profile/{profileId}")
	public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecords(@PathVariable Long profileId) {
		return ResponseEntity.ok(
				mediaRecordMapper.toDTO(mediaRecordService.getAllMedicalRecordsByProfileId(profileId)));
	}
}
