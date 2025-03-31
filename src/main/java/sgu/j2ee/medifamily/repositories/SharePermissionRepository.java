package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.SharePermission;

@RepositoryRestResource(collectionResourceRel = "sharepermissions", path = "sharepermissions")
public interface SharePermissionRepository extends JpaRepository<SharePermission, Long> {
}
