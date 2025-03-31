package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder {
	public enum ReminderType {
		APPOINTMENT, MEDICATION, VACCINATION, OTHER
	}

	public enum Priority {
		LOW, MEDIUM, HIGH
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private FamilyMember member;

	private String title;
	private String description;
	private LocalDate reminderDate;
	private LocalDateTime reminderTime;

	@Enumerated(EnumType.STRING)
	private ReminderType reminderType;

	@Enumerated(EnumType.STRING)
	private Priority priority;

	private Boolean isRecurring;
	private String recurrencePattern;
	private Boolean isCompleted;
	private LocalDate completedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
}
