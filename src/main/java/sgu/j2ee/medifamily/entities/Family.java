package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "families")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Family {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Tên gia đình không được để trống")
	@Size(min = 3, max = 255, message = "Tên gia đình phải có từ 3 đến 255 ký tự")
	private String familyName;

	@NotBlank(message = "Địa chỉ không được để trống")
	@Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
	private String address;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id", nullable = false)
	@NotNull(message = "Chủ hộ không được để trống")
	private User owner;

	@Pattern(regexp = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ")
	private String phoneNumber;

	@Email(message = "Email không hợp lệ")
	@Size(max = 100, message = "Email không được vượt quá 100 ký tự")
	private String email;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@NotNull(message = "Trạng thái hoạt động không được để trống")
	private Boolean isActive;

	@Transient
	public List<FamilyMember> familyMembers;
}
