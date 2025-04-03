package sgu.j2ee.medifamily.dtos.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTO {
	private Long id;
	private String bloodType;
	private Double height;
	private Double weight;
	private String allergies;
	private String chronicDiseases;
	private String notes;
	private String healthInsuranceNumber;
	private String fullName;
	private String phoneNumber;
	private LocalDate dateOfBirth;
	private Gender gender;
	private String email;
	private String address;
	private String avatarUrl;
	private String bio;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Long userId;
}
