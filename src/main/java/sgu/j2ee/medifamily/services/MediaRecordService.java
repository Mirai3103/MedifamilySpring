package sgu.j2ee.medifamily.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sgu.j2ee.medifamily.entities.MedicalRecord;
import sgu.j2ee.medifamily.repositories.MedicalRecordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaRecordService {
    private final MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecordsByProfileId(Long profileId) {
        return medicalRecordRepository.findAllByProfileId(profileId);
    }
}
