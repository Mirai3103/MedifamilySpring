package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.PrescriptionItem;

@RepositoryRestResource(collectionResourceRel = "prescriptionitems", path = "prescriptionitems")
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
}
