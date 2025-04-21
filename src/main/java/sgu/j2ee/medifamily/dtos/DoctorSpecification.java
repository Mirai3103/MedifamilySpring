package sgu.j2ee.medifamily.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import sgu.j2ee.medifamily.entities.Doctor;

public class DoctorSpecification {

	public static Specification<Doctor> withFilters(DoctorSearchRequest request) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (request.getUserId() != null) {
				predicates.add(cb.equal(root.get("user").get("id"), request.getUserId()));
			}

			if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
				String likePattern = "%" + request.getKeyword().toLowerCase() + "%";

				Predicate specialtyLike = cb.like(cb.lower(root.get("specialty")), likePattern);
				Predicate licenseLike = cb.like(cb.lower(root.get("licenseNumber")), likePattern);
				Predicate facilityLike = cb.like(cb.lower(root.get("medicalFacility")), likePattern);
				Predicate userNameLike = cb.like(cb.lower(root.get("user").get("profile").get("fullName")),
						likePattern);

				predicates.add(cb.or(specialtyLike, licenseLike, facilityLike, userNameLike));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}