package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.ActivityLog;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
