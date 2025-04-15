package sgu.j2ee.medifamily.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import lombok.Data;
import sgu.j2ee.medifamily.entities.ShareProfile;

@Data
@ParameterObject
public class ShareProfileQuery {
	private Long familyId;
	private Long memberId;

	public Specification<ShareProfile> toSpecification() {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (familyId != null) {
				predicates.add(cb.equal(root.get("family").get("id"), familyId));
			}

			if (memberId != null) {
				predicates.add(cb.equal(root.get("member").get("id"), memberId));
			}
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
