package sgu.j2ee.medifamily.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sgu.j2ee.medifamily.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
