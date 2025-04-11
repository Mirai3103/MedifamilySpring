package sgu.j2ee.medifamily.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.Prescription;

@RepositoryRestResource(collectionResourceRel = "prescriptions", path = "prescriptions")
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
	List<Prescription> findAllByMedicalRecordProfileId(Long profileId);

	List<Prescription> findAllByMedicalRecordId(Long id);
}
