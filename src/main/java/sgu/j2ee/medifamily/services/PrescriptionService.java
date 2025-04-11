package sgu.j2ee.medifamily.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.Prescription;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.PrescriptionItemRepository;
import sgu.j2ee.medifamily.repositories.PrescriptionRepository;

@Service
@RequiredArgsConstructor
public class PrescriptionService {
	private final PrescriptionRepository prescriptionRepository;
	private final PrescriptionItemRepository prescriptionItemRepository;
	private final MediaRecordService mediaRecordService;
	// todo: crud methods for prescription

	public List<Prescription> findAllByProfileId(Long profileId) {
		return prescriptionRepository.findAllByMedicalRecordProfileId(profileId);
	}

	public List<Prescription> findAllByMedicalRecordId(Long medicalRecordId) {
		return prescriptionRepository.findAllByMedicalRecordId(medicalRecordId);
	}

	public Prescription findById(Long id) {
		return prescriptionRepository.findById(id).orElse(null);
	}

	public Prescription createPrescription(Prescription prescription) {
		if (prescription.getMedicalRecordId() != null) {
			var medicalRecord = mediaRecordService.getMedicalRecordById(prescription.getMedicalRecordId());
			if (medicalRecord == null) {
				throw new NotFoundException("Medical record not found");
			}
			prescription.setMedicalRecord(medicalRecord);
		}
		return prescriptionRepository.save(prescription);
	}

	public void deletePrescription(Long id) {
		prescriptionRepository.deleteById(id);
	}
}
