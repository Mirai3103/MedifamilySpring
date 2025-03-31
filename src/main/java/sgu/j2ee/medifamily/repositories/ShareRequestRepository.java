package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.ShareRequest;

@RepositoryRestResource(collectionResourceRel = "sharerequests", path = "sharerequests")
public interface ShareRequestRepository extends JpaRepository<ShareRequest, Long> {
}
