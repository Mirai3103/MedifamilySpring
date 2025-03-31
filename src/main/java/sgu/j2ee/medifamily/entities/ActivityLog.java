package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activity_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {
	public enum ActionType {
		CREATE, UPDATE, DELETE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	private String entityType;
	private String entityId;
	private String description;
	private String ipAddress;
	private String userAgent;
}
