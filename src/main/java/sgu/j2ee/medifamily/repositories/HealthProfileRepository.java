package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.HealthProfile;

@RepositoryRestResource(collectionResourceRel = "healthprofiles", path = "healthprofiles")
public interface HealthProfileRepository extends JpaRepository<HealthProfile, Long> {
}
