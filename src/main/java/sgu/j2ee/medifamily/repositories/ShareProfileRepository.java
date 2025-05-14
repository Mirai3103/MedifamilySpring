package sgu.j2ee.medifamily.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sgu.j2ee.medifamily.entities.ShareProfile;

public interface ShareProfileRepository
		extends JpaRepository<ShareProfile, UUID>, JpaSpecificationExecutor<ShareProfile> {
	@Query(value = """
			SELECT * FROM share_profiles
			WHERE share_type = 'OnlyInvitedPeople'
			  AND (expires_at IS NULL OR expires_at > now())
			  AND invited_emails @> cast(to_jsonb(array[:email]) as jsonb)
			""", nativeQuery = true)
	List<ShareProfile> getShareProfilesWithMe(@Param("email") String email);
}
