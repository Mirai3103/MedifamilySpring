package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import sgu.j2ee.medifamily.dtos.family.FamilyDTO;
import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.entities.FamilyDoctor;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.FamilyDoctor}
 */
@Data
public class FamilyDoctorDto implements Serializable {
	private Long id;
	private FamilyDTO family;
	private Long familyId;
	private DoctorDTO doctor;
	private Long doctorId;
	private LocalDate startDate;
	private LocalDate endDate;
	private FamilyDoctor.Status status;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}