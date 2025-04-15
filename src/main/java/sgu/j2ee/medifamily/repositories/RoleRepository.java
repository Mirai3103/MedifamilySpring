package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
