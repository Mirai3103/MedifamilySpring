package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.PrescriptionItem}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItemDto implements Serializable {
	private Long id;
	@NotNull(message = "Thông tin đơn thuốc không được để trống")
	private Long prescriptionId;
	@Size(message = "Tên thuốc phải có từ 2 đến 200 ký tự", min = 2, max = 200)
	@NotBlank(message = "Tên thuốc không được để trống")
	private String medicationName;
	@Size(message = "Liều dùng phải có từ 1 đến 100 ký tự", min = 1, max = 100)
	@NotBlank(message = "Liều dùng không được để trống")
	private String dosage;
	@Size(message = "Tần suất sử dụng phải có từ 1 đến 100 ký tự", min = 1, max = 100)
	@NotBlank(message = "Tần suất sử dụng không được để trống")
	private String frequency;
	@Min(message = "Số lượng phải lớn hơn hoặc bằng 1", value = 1)
	private Integer durationInDays;
	@NotNull(message = "Ngày bắt đầu sử dụng không được để trống")
	private LocalDate startUseDate;
	@Size(message = "Hướng dẫn sử dụng không được vượt quá 500 ký tự", max = 500)
	private String instructions;
	@Size(message = "Ghi chú không được vượt quá 1000 ký tự", max = 1000)
	private String notes;
}