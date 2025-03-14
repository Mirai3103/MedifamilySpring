package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Prescription;

@RepositoryRestResource(collectionResourceRel = "prescriptions", path = "prescriptions")
public interface PrescriptionRepository extends PagingAndSortingRepository<Prescription, Long> {
}
