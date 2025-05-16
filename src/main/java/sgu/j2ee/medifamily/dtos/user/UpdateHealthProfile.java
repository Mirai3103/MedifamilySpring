package sgu.j2ee.medifamily.dtos.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateHealthProfile {

	private Long id;

	@Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Nhóm máu phải có định dạng hợp lệ (A+, B-, AB+, O-,...)")
	private String bloodType;

	@DecimalMin(value = "0.0", message = "Chiều cao phải là số dương")
	@DecimalMax(value = "300.0", message = "Chiều cao không được vượt quá 300cm")
	private Double height;

	@DecimalMin(value = "0.0", message = "Cân nặng phải là số dương")
	@DecimalMax(value = "500.0", message = "Cân nặng không được vượt quá 500kg")
	private Double weight;

	@Size(max = 1000, message = "Thông tin dị ứng không được vượt quá 1000 ký tự")
	private String allergies;

	@Size(max = 1000, message = "Thông tin bệnh mãn tính không được vượt quá 1000 ký tự")
	private String chronicDiseases;

	@Size(max = 2000, message = "Ghi chú không được vượt quá 2000 ký tự")
	private String notes;
	@NotBlank
	private String healthInsuranceNumber;
}
