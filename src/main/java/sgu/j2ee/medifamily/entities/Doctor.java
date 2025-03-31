package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull(message = "Thông tin người dùng không được để trống")
	private User user;

	@NotBlank(message = "Chuyên khoa không được để trống")
	@Size(min = 2, max = 100, message = "Chuyên khoa phải có từ 2 đến 100 ký tự")
	private String specialty;

	@NotBlank(message = "Số giấy phép hành nghề không được để trống")
	@Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Số giấy phép hành nghề phải có từ 5-20 ký tự bao gồm chữ hoa và số")
	private String licenseNumber;

	@NotBlank(message = "Cơ sở y tế không được để trống")
	@Size(min = 2, max = 200, message = "Tên cơ sở y tế phải có từ 2 đến 200 ký tự")
	private String medicalFacility;

	@Size(max = 1000, message = "Thông tin giới thiệu không được vượt quá 1000 ký tự")
	private String bio;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@NotNull(message = "Trạng thái xác thực không được để trống")
	private Boolean isVerified;
}
