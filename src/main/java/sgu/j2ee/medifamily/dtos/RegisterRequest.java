package sgu.j2ee.medifamily.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Data
public class RegisterRequest {

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 8, max = 100, message = "Mật khẩu phải có từ 8 đến 100 ký tự")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Mật khẩu phải chứa ít nhất 1 chữ thường, 1 chữ hoa, 1 số và 1 ký tự đặc biệt")
	private String password;

	@NotBlank(message = "Họ tên không được để trống")
	@Size(min = 2, max = 100, message = "Họ tên phải có từ 2 đến 100 ký tự")
	private String fullName;

	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không hợp lệ")
	@Size(max = 100, message = "Email không được vượt quá 100 ký tự")
	private String email;

	@NotNull(message = "Ngày sinh không được để trống")
	@Past(message = "Ngày sinh phải là ngày trong quá khứ")
	private LocalDate dateOfBirth;

	private Gender gender = Gender.MALE;

	public boolean getIsDoctor() {
		return doctor != null;
	}

	@Data
	public static class DoctorInFo {
		@Size(min = 2, max = 100, message = "Chuyên khoa phải có từ 2 đến 100 ký tự")
		private String specialty;

		@Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Số giấy phép hành nghề phải có từ 5-20 ký tự bao gồm chữ hoa và số")
		private String licenseNumber;

		@Size(min = 2, max = 200, message = "Tên cơ sở y tế phải có từ 2 đến 200 ký tự")
		private String medicalFacility;

		@Size(max = 1000, message = "Thông tin giới thiệu không được vượt quá 1000 ký tự")
		private String bio;
	}

	private DoctorInFo doctor;

}
