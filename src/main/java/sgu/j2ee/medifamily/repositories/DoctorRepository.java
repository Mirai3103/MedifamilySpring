package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sgu.j2ee.medifamily.entities.Doctor;

@RepositoryRestResource(collectionResourceRel = "doctors", path = "doctors")
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
