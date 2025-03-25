package sgu.j2ee.medifamily.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Family;

@RepositoryRestResource(collectionResourceRel = "familys", path = "familys")
public interface FamilyRepository extends JpaRepository<Family, Long> {
    @Query("SELECT fm FROM Family f join FamilyMember fm on f.id = fm.family.id WHERE fm.user.id = ?1")
    public List<Family> findByFamilyUserId(Long userId);
}
