package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
	@Id
	@GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
	private Long id;
	// health profile
	@Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Nhóm máu phải có định dạng hợp lệ (A+, B-, AB+, O-,...)")
	private String bloodType;

	@DecimalMin(value = "0.0", message = "Chiều cao phải là số dương")
	@DecimalMax(value = "300.0", message = "Chiều cao không được vượt quá 300cm")
	private Double height;

	@DecimalMin(value = "0.0", message = "Cân nặng phải là số dương")
	@DecimalMax(value = "500.0", message = "Cân nặng không được vượt quá 500kg")
	private Double weight;

	@Size(max = 1000, message = "Thông tin dị ứng không được vượt quá 1000 ký tự")
	@Builder.Default
	private String allergies = "Không có ";

	@Size(max = 1000, message = "Thông tin bệnh mãn tính không được vượt quá 1000 ký tự")
	@Builder.Default
	private String chronicDiseases = "Không có ";

	@Size(max = 2000, message = "Ghi chú không được vượt quá 2000 ký tự")
	private String notes;

	@Size(max = 20, message = "Số bảo hiểm y tế không được vượt quá 20 ký tự")
	private String healthInsuranceNumber;

	// person profile
	private String fullName;
	private String phoneNumber;
	private LocalDate dateOfBirth;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String email;
	private String address;
	private String avatarUrl;
	private String bio;

	@OneToOne(fetch = jakarta.persistence.FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@OneToMany(mappedBy = "profile", fetch = jakarta.persistence.FetchType.LAZY)
	@Builder.Default
	private List<FamilyMember> familyMembers = new ArrayList<>();

	@CreatedDate
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;
}
