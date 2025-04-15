package sgu.j2ee.medifamily.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Optional<Profile> findFirstByUserId(Long userId);
}
