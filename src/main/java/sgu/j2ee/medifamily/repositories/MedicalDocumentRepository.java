package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.MedicalDocument;

@RepositoryRestResource(collectionResourceRel = "medicaldocuments", path = "medicaldocuments")
public interface MedicalDocumentRepository extends JpaRepository<MedicalDocument, Long> {
}
