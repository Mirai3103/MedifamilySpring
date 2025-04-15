package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
