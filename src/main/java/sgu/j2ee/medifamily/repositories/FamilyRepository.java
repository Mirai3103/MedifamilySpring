package sgu.j2ee.medifamily.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sgu.j2ee.medifamily.entities.Family;

public interface FamilyRepository extends JpaRepository<Family, Long> {
	@Query("SELECT f FROM Family f join FamilyMember fm on f.id = fm.family.id WHERE fm.profile.user.id = ?1")
	public List<Family> findByFamilyUserId(Long userId);
}
