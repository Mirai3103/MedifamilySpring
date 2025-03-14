package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.FamilyDoctor;

@RepositoryRestResource(collectionResourceRel = "familydoctors", path = "familydoctors")
public interface FamilyDoctorRepository extends PagingAndSortingRepository<FamilyDoctor, Long> {
}
