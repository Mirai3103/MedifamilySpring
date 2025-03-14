package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.TransferRequest;

@RepositoryRestResource(collectionResourceRel = "transferrequests", path = "transferrequests")
public interface TransferRequestRepository extends PagingAndSortingRepository<TransferRequest, Long> {
}
