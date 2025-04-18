package sgu.j2ee.medifamily.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import sgu.j2ee.medifamily.entities.FamilyMember;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
	List<FamilyMember> findByFamilyId(Long familyId);

	void deleteByFamilyIdAndProfileId(Long familyId, Long profileId);

	Optional<FamilyMember> findByFamilyIdAndId(Long familyId, Long id);
}
