package sgu.j2ee.medifamily.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
	@NotBlank
	private String oldPassword;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 8, max = 100, message = "Mật khẩu phải có từ 8 đến 100 ký tự")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Mật khẩu phải chứa ít nhất 1 chữ thường, 1 chữ hoa, 1 số và 1 ký tự đặc biệt")
	private String newPassword;
}
