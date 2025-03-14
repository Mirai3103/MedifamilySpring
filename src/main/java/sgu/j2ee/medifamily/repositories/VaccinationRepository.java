package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Vaccination;

@RepositoryRestResource(collectionResourceRel = "vaccinations", path = "vaccinations")
public interface VaccinationRepository extends PagingAndSortingRepository<Vaccination, Long> {
}
