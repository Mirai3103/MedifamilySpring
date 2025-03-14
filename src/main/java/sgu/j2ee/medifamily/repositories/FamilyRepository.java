package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Family;

@RepositoryRestResource(collectionResourceRel = "familys", path = "familys")
public interface FamilyRepository extends PagingAndSortingRepository<Family, Long> {
}
