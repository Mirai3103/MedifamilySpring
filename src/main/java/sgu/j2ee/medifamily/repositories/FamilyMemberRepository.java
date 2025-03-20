package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.FamilyMember;

@RepositoryRestResource(collectionResourceRel = "familymembers", path = "familymembers")
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
}
