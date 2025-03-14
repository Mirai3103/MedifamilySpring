package sgu.j2ee.medifamily.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.FamilyMember;

@RepositoryRestResource(collectionResourceRel = "familymembers", path = "familymembers")
public interface FamilyMemberRepository extends PagingAndSortingRepository<FamilyMember, Long> {
}
