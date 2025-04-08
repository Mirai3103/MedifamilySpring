package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;
import sgu.j2ee.medifamily.dtos.user.UserDTO;
import sgu.j2ee.medifamily.entities.HealthMetric;

/**
 * DTO for {@link HealthMetric}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HealthMetricDto implements Serializable {
	private Long id;
	private ProfileDTO profile;
	private Long profileId;
	private HealthMetric.MetricType metricType;
	private Double metricValue;
	private String unit;
	private LocalDate measurementDate;
	private LocalDateTime measurementTime;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UserDTO createdBy;
}