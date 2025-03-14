package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.UserRole;

@RepositoryRestResource(collectionResourceRel = "userroles", path = "userroles")
public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long> {
}
