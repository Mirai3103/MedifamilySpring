package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthMetric {
    public enum MetricType {
        BLOOD_PRESSURE,
        HEART_RATE,
        BLOOD_SUGAR,
        TEMPERATURE,
        OXYGEN_SATURATION,
        WEIGHT,
        HEIGHT,
        BMI,
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private FamilyMember member;

    @Enumerated(EnumType.STRING)
    private MetricType metricType;

    private Double metricValue;
    private String unit;
    private LocalDate measurementDate;
    private LocalDateTime measurementTime;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}