package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
	public enum NotificationType {
		INFO, WARNING, ERROR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private String title;
	private String content;
	private Boolean isRead;
	private LocalDateTime readAt;

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	private String referenceId;
	private String referenceType;
	private LocalDateTime createdAt;
}
