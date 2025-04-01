package sgu.j2ee.medifamily.dtos.user;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Data
public class UpdateProfileRequest {

	@NotNull(message = "ID không được để trống")
	private Long id;

	@Email(message = "Email không hợp lệ")
	@NotBlank(message = "Email không được để trống")
	private String email;

	@NotBlank(message = "Họ và tên không được để trống")
	@Size(max = 100, message = "Họ và tên không được quá 100 ký tự")
	private String fullName;

	@Pattern(regexp = "^\\d{10,15}$", message = "Số điện thoại không hợp lệ")
	private String phoneNumber;

	@Past(message = "Ngày sinh phải là ngày trong quá khứ")
	private LocalDate dateOfBirth;

	@NotNull(message = "Giới tính không được để trống")
	private Gender gender;

	@Size(max = 255, message = "Địa chỉ không được quá 255 ký tự")
	private String address;

	private String bio;

}
