package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "health_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class HealthMetric {
	public enum MetricType {
		BLOOD_PRESSURE, HEART_RATE, BLOOD_SUGAR, TEMPERATURE, OXYGEN_SATURATION, WEIGHT, HEIGHT, BMI,
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Profile profile;
	@Column(name = "profile_id", insertable = false, updatable = false)
	private Long profileId;

	@Enumerated(EnumType.STRING)
	private MetricType metricType;

	private Double metricValue;
	private String unit;
	private LocalDate measurementDate;
	private LocalDateTime measurementTime;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;
}
