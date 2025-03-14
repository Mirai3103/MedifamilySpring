package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.MedicalDocument;

@RepositoryRestResource(collectionResourceRel = "medicaldocuments", path = "medicaldocuments")
public interface MedicalDocumentRepository extends PagingAndSortingRepository<MedicalDocument, Long> {
}
