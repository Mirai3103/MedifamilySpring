package sgu.j2ee.medifamily.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.Profile;

@RepositoryRestResource(collectionResourceRel = "profiles", path = "profiles")
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findFirstByUserId(Long userId);
}
