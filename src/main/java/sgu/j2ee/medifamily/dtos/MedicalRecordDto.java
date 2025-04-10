package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;
import sgu.j2ee.medifamily.dtos.user.UserDTO;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.MedicalRecord}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MedicalRecordDto implements Serializable {
	private Long id;
	private ProfileDTO profile;
	private String title;
	private Long profileId;

	private LocalDate visitDate;

	private String medicalFacility;
	private String doctorName;
	private DoctorDTO doctor;
	private String diagnosis;
	private String treatment;
	private String notes;
	private Boolean isFollowup;
	private LocalDate followupDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private UserDTO createdBy;
}