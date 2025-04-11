package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.Prescription}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto implements Serializable {
	private Long id;
	private Long medicalRecordId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<PrescriptionItemDto> items = List.of();
	private String createdBy;
}