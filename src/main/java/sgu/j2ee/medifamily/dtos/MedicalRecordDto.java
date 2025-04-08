package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sgu.j2ee.medifamily.dtos.user.DoctorDTO;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;
import sgu.j2ee.medifamily.dtos.user.UserDTO;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.MedicalRecord}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MedicalRecordDto implements Serializable {
	private Long id;
	private ProfileDTO profile;
	private String title;
	private Long profileId;
	@NotNull(message = "Ngày khám không được để trống")
	@PastOrPresent(message = "Ngày khám phải là ngày hiện tại hoặc trong quá khứ")
	private LocalDate visitDate;
	@Size(message = "Tên cơ sở y tế phải có từ 2 đến 200 ký tự", min = 2, max = 200)
	@NotBlank(message = "Cơ sở y tế không được để trống")
	private String medicalFacility;
	@Size(message = "Tên bác sĩ phải có từ 2 đến 100 ký tự", min = 2, max = 100)
	private String doctorName;
	private DoctorDTO doctor;
	@Size(message = "Chẩn đoán phải có từ 2 đến 500 ký tự", min = 2, max = 500)
	@NotBlank(message = "Chẩn đoán không được để trống")
	private String diagnosis;
	@Size(message = "Phương pháp điều trị không được vượt quá 1000 ký tự", max = 1000)
	private String treatment;
	@Size(message = "Ghi chú không được vượt quá 2000 ký tự", max = 2000)
	private String notes;
	private Boolean isFollowup;
	@Future(message = "Ngày tái khám phải là ngày trong tương lai")
	private LocalDate followupDate;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@NotNull(message = "Người tạo hồ sơ không được để trống")
	private UserDTO createdBy;
}