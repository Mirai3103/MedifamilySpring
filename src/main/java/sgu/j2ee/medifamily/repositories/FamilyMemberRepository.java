package sgu.j2ee.medifamily.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.FamilyMember;

@RepositoryRestResource(collectionResourceRel = "familymembers", path = "familymembers")
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
	List<FamilyMember> findByFamilyId(Long familyId);

	void deleteByFamilyIdAndProfileId(Long familyId, Long profileId);

	Optional<FamilyMember> findByFamilyIdAndProfileId(Long familyId, Long profileId);
}
