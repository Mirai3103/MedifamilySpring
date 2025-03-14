package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.PrescriptionItem;

@RepositoryRestResource(collectionResourceRel = "prescriptionitems", path = "prescriptionitems")
public interface PrescriptionItemRepository extends PagingAndSortingRepository<PrescriptionItem, Long> {
}
