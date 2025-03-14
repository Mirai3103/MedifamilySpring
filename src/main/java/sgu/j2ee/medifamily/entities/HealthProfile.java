package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private FamilyMember member;

    private String bloodType;
    private Double height;
    private Double weight;
    private String allergies;
    private String chronicDiseases;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
