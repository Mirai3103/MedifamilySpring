package sgu.j2ee.medifamily.dtos.family;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import sgu.j2ee.medifamily.entities.enums.Gender;

@Data
public class MemberProfile {
    
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải có từ 2 đến 100 ký tự")
    private String fullName;

    @NotNull(message = "Vui lòng chọn giới tính")
    private Gender gender;

    @NotBlank(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")

    private LocalDate birthDate;

    @Pattern(
        regexp = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$",
        message = "Số điện thoại không hợp lệ"
    )
    private String phoneNumber;

    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;
}
