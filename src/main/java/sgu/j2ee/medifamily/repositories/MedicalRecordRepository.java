package sgu.j2ee.medifamily.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sgu.j2ee.medifamily.entities.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
	List<MedicalRecord> findAllByProfileIdOrderByCreatedAtDesc(Long profileId);

	@Query("""
			    SELECT m FROM MedicalRecord m
			    WHERE m.isFollowup = true
			      AND m.followupDate = :tomorrow
			""")
	List<MedicalRecord> findFollowupsTomorrow(@Param("tomorrow") LocalDate tomorrow);
}
