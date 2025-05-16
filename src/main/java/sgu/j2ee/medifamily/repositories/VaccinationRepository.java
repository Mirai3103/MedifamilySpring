package sgu.j2ee.medifamily.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sgu.j2ee.medifamily.entities.Vaccination;

public interface VaccinationRepository extends JpaRepository<Vaccination, Long>, JpaSpecificationExecutor<Vaccination> {
	@Query("SELECT v FROM Vaccination v WHERE v.isDone = false AND v.vaccinationDate >= :start AND v.vaccinationDate < :end")
	List<Vaccination> findVaccinationsByDateRange(
			@Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);

}
