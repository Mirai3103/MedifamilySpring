package sgu.j2ee.medifamily.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
	List<Prescription> findAllByMedicalRecordProfileId(Long profileId);

	Prescription findFirstByMedicalRecordId(Long id);
}
