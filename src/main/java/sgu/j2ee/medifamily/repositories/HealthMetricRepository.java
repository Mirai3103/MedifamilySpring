package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.HealthMetric;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
}
