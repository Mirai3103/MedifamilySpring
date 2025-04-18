package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sgu.j2ee.medifamily.entities.Vaccination;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long>, JpaSpecificationExecutor<Vaccination> {
}
