package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "share_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShareRequest {
	public enum Status {
		PENDING, APPROVED, REJECTED
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "family_id", nullable = false)
	private Family family;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private FamilyMember member;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	private String shareToken;
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String reason;
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private User approvedBy;
}
