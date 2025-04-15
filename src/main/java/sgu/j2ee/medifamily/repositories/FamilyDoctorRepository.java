package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.FamilyDoctor;

public interface FamilyDoctorRepository extends JpaRepository<FamilyDoctor, Long> {
}
