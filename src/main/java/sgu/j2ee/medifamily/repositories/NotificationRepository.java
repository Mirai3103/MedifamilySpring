package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import sgu.j2ee.medifamily.entities.Notification;

@RepositoryRestResource(collectionResourceRel = "notifications", path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
