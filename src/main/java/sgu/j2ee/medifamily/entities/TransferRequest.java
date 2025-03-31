package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequest {
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
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

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
}