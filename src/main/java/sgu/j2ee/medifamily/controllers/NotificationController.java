package sgu.j2ee.medifamily.controllers;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.Notification;
import sgu.j2ee.medifamily.repositories.NotificationRepository;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
	private final NotificationRepository notificationRepository;
	private final UserDetailsServiceImpl userDetailsService;

	@GetMapping("my-notifications")
	public PagedModel<Notification> getMyNotifications(@PageableDefault(size = 20) Pageable pageable) {
		var currentUser = userDetailsService.getCurrentUser();
		var notifications = notificationRepository.findAllByUserIdOrderByCreatedAtDesc(currentUser.getId(), pageable);
		return new PagedModel<>(notifications.map(notification -> {
			notification.setUser(null);
			return notification;
		}));
	}

	@PatchMapping("read/{id}")
	public void markAsRead(@PathVariable String id) {
		var notification = notificationRepository.findById(Long.parseLong(id)).orElseThrow();
		notification.setIsRead(true);
		notification.setReadAt(LocalDateTime.now());
		notificationRepository.save(notification);
	}

	@PatchMapping("read-all")
	public void markAllAsRead() {
		var currentUser = userDetailsService.getCurrentUser();
		notificationRepository.updateAllNotificationsAsRead(currentUser.getId());

	}
}
