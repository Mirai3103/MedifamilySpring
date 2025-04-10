package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.MedicalRecord;
import sgu.j2ee.medifamily.repositories.MedicalRecordRepository;

@Service
@RequiredArgsConstructor
public class MediaRecordService {
	private final MedicalRecordRepository medicalRecordRepository;

	public List<MedicalRecord> getAllMedicalRecordsByProfileId(Long profileId) {
		return medicalRecordRepository.findAllByProfileId(profileId);
	}
}
