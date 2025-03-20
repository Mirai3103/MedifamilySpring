package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.HealthMetric;

@RepositoryRestResource(collectionResourceRel = "healthmetrics", path = "healthmetrics")
public interface HealthMetricRepository extends JpaRepository<HealthMetric, Long> {
}
