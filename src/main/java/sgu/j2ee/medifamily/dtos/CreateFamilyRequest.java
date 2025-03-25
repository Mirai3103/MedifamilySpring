package sgu.j2ee.medifamily.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class CreateFamilyRequest {
    @NotBlank(message = "Tên gia đình không được để trống")
    @Size(min = 3, max = 255, message = "Tên gia đình phải có từ 3 đến 255 ký tự")
    private String familyName;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 500, message = "Địa chỉ không được vượt quá 500 ký tự")
    private String address;


    @Pattern(regexp = "^(\\+84|0)[3|5|7|8|9][0-9]{8}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;


    @Email(message = "Email không hợp lệ")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;


    @Min(value = 1, message = "Người tạo gia đình không hợp lệ")
    @NotNull(message = "Người tạo gia đình không được để trống")
    private Long createdBy;
}
