package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.ActivityLog;

@RepositoryRestResource(collectionResourceRel = "activitylogs", path = "activitylogs")
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}
