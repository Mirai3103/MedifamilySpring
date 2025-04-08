package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.MedicalRecordDto;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {
	@GetMapping()
	public ResponseEntity<List<MedicalRecordDto>> getAllMedicalRecords() {
		return ResponseEntity.ok(List.of());
	}
}
