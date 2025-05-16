package sgu.j2ee.medifamily.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import sgu.j2ee.medifamily.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	Page<Notification> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

	@Modifying
	@org.springframework.transaction.annotation.Transactional
	@Query("UPDATE Notification n SET n.isRead = true WHERE n.user.id = :userId")
	void updateAllNotificationsAsRead(Long userId);
}
