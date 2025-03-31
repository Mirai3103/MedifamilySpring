package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
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

	@ManyToOne
	@JoinColumn(name = "prescription_id", nullable = false)
	@NotNull(message = "Thông tin đơn thuốc không được để trống")
	private Prescription prescription;

	@NotBlank(message = "Tên thuốc không được để trống")
	@Size(min = 2, max = 200, message = "Tên thuốc phải có từ 2 đến 200 ký tự")
	private String medicationName;

	@NotBlank(message = "Liều dùng không được để trống")
	@Size(min = 1, max = 100, message = "Liều dùng phải có từ 1 đến 100 ký tự")
	private String dosage;

	@NotBlank(message = "Tần suất sử dụng không được để trống")
	@Size(min = 1, max = 100, message = "Tần suất sử dụng phải có từ 1 đến 100 ký tự")
	private String frequency;

	@Size(max = 100, message = "Thời gian dùng thuốc không được vượt quá 100 ký tự")
	private String duration;

	@Size(max = 500, message = "Hướng dẫn sử dụng không được vượt quá 500 ký tự")
	private String instructions;

	@Size(max = 1000, message = "Ghi chú không được vượt quá 1000 ký tự")
	private String notes;
}
