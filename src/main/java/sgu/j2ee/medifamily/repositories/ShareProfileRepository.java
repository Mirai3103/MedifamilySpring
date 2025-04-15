package sgu.j2ee.medifamily.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sgu.j2ee.medifamily.entities.ShareProfile;

public interface ShareProfileRepository
		extends JpaRepository<ShareProfile, UUID>, JpaSpecificationExecutor<ShareProfile> {
}
