package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "transfer_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class TransferRequest {
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
	@JoinColumn(name = "source_doctor_id", nullable = false)
	private Doctor sourceDoctor;

	@ManyToOne
	@JoinColumn(name = "target_doctor_id", nullable = false)
	private Doctor targetDoctor;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Enumerated(EnumType.STRING)
	private Status status;

	private String reason;
	private String notes;

	@CreatedBy
	private String createdBy;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private User approvedBy;
}
