package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "family_doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyDoctor {
    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = false)
    private Family family;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}