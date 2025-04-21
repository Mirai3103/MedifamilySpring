package sgu.j2ee.medifamily.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sgu.j2ee.medifamily.entities.FamilyDoctor;

public interface FamilyDoctorRepository
		extends JpaRepository<FamilyDoctor, Long>, JpaSpecificationExecutor<FamilyDoctor> {
	Optional<FamilyDoctor> findFirstByFamilyId(Long familyId);
}
