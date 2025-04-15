package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.SharePermission;

public interface SharePermissionRepository extends JpaRepository<SharePermission, Long> {
}
