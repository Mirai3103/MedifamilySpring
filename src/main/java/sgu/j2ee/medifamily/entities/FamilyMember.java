package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.Builder.Default;

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

	@Column (name = "family_id", insertable = false, updatable = false)
	private Long familyId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;

	@Column (name = "profile_id", insertable = false, updatable = false)
	private Long profileId;

	@NotBlank(message = "Quan hệ với chủ hộ không được để trống")
	@Size(max = 50, message = "Quan hệ với chủ hộ không được vượt quá 50 ký tự")
	private String relationship;
	@CreatedDate
	private LocalDateTime createdAt;
	@Default
	private Boolean isActive = true;
}
