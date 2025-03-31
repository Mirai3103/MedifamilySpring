package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.MedicalRecord;

@RepositoryRestResource(collectionResourceRel = "medicalrecords", path = "medicalrecords")
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
