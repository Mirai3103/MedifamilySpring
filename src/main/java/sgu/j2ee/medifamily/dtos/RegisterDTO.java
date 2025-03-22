package sgu.j2ee.medifamily.dtos;


import jakarta.validation.constraints.*;
import lombok.Data;
import sgu.j2ee.medifamily.entities.enums.Gender;

import java.time.LocalDate;

@Data


public class RegisterDTO {
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 50, message = "Tên đăng nhập phải có từ 4 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Tên đăng nhập chỉ được chứa chữ cái, số và dấu gạch dưới")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, max = 100, message = "Mật khẩu phải có từ 8 đến 100 ký tự")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Mật khẩu phải chứa ít nhất 1 chữ thường, 1 chữ hoa, 1 số và 1 ký tự đặc biệt")
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

    private boolean isDoctor;
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

    @AssertTrue(message = "Thông tin bác sĩ không được để trống khi đăng ký là bác sĩ")
    private boolean isDoctorInfoValid() {
        return !isDoctor || doctor != null;
    }
}