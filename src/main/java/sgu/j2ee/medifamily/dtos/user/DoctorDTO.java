package sgu.j2ee.medifamily.dtos.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
	private Long id;
	private String specialty;
	private String licenseNumber;
	private String medicalFacility;
	private String bio;
	private Boolean isVerified;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
