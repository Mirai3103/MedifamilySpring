package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "prescription_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "prescription_id", nullable = false)
	@NotNull(message = "Thông tin đơn thuốc không được để trống")
	private Long prescriptionId;

	@NotBlank(message = "Tên thuốc không được để trống")
	@Size(min = 2, max = 200, message = "Tên thuốc phải có từ 2 đến 200 ký tự")
	private String medicationName;

	@NotBlank(message = "Liều dùng không được để trống")
	@Size(min = 1, max = 100, message = "Liều dùng phải có từ 1 đến 100 ký tự")
	private String dosage;

	@NotBlank(message = "Tần suất sử dụng không được để trống")
	@Size(min = 1, max = 100, message = "Tần suất sử dụng phải có từ 1 đến 100 ký tự")
	private String frequency;

	@Min(value = 1, message = "Số lượng phải lớn hơn hoặc bằng 1")
	private Integer durationInDays;

	@NotNull(message = "Ngày bắt đầu sử dụng không được để trống")
	private LocalDate startUseDate;

	@Size(max = 500, message = "Hướng dẫn sử dụng không được vượt quá 500 ký tự")
	private String instructions;

	@Size(max = 1000, message = "Ghi chú không được vượt quá 1000 ký tự")
	private String notes;
}
