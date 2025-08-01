package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.MedicalDocument;

public interface MedicalDocumentRepository extends JpaRepository<MedicalDocument, Long> {
}
