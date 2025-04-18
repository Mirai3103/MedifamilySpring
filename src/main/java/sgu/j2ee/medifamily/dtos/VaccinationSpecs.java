package sgu.j2ee.medifamily.dtos;

import org.springframework.data.jpa.domain.Specification;
import sgu.j2ee.medifamily.entities.Vaccination;

import java.time.LocalDateTime;

public class VaccinationSpecs {

    public static Specification<Vaccination> byProfileId(Long profileId) {
        return (root, query, cb) -> cb.equal(root.get("profileId"), profileId);
    }

    public static Specification<Vaccination> keywordLike(String keyword) {
        return (root, query, cb) -> {
            String like = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("vaccineName")), like),
                    cb.like(cb.lower(root.get("batchNumber")), like),
                    cb.like(cb.lower(root.get("location")), like)
            );
        };
    }

    public static Specification<Vaccination> vaccinationBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> cb.between(root.get("vaccinationDate"), from, to);
    }
}
