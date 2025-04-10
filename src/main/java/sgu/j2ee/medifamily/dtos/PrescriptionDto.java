package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sgu.j2ee.medifamily.dtos.user.UserDTO;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.Prescription}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PrescriptionDto implements Serializable {
	private Long id;
	private MedicalRecordDto medicalRecord;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UserDTO createdBy;
	private List<PrescriptionItemDto> prescriptionItems = List.of();
}