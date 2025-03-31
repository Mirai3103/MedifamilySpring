package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.Builder.Default;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Entity
@Table(name = "family_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "family_id", nullable = false)
	@NotNull(message = "Thông tin gia đình không được để trống")
	private Family family;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "health_profile_id")
	private HealthProfile healthProfile;

	private String avatarUrl;

	private String fullName;

	private LocalDate dateOfBirth;

	private Gender gender;

	@NotBlank(message = "Quan hệ với chủ hộ không được để trống")
	@Size(max = 50, message = "Quan hệ với chủ hộ không được vượt quá 50 ký tự")
	private String relationship;

	private String phoneNumber;

	private String email;

	private boolean isHasAccount;

	@CreatedDate
	private LocalDateTime createdAt;
	@Default
	private Boolean isActive = true;
}
