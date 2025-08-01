package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MedicalRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Profile profile;
	@Column(name = "profile_id", insertable = false, updatable = false)

	private Long profileId;

	@NotNull(message = "Ngày khám không được để trống")
	// @PastOrPresent(message = "Ngày khám phải là ngày hiện tại hoặc trong quá
	// khứ")
	private LocalDate visitDate;

	@NotBlank(message = "Cơ sở y tế không được để trống")
	@Size(min = 2, max = 200, message = "Tên cơ sở y tế phải có từ 2 đến 200 ký tự")
	private String medicalFacility;

	@Size(min = 2, max = 100, message = "Tên bác sĩ phải có từ 2 đến 100 ký tự")
	private String doctorName;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = true)
	private Doctor doctor;

	@NotBlank(message = "Chẩn đoán không được để trống")
	@Size(min = 2, max = 500, message = "Chẩn đoán phải có từ 2 đến 500 ký tự")
	private String diagnosis;

	@Size(max = 1000, message = "Phương pháp điều trị không được vượt quá 1000 ký tự")
	private String treatment;

	@Size(max = 2000, message = "Ghi chú không được vượt quá 2000 ký tự")
	private String notes;

	private Boolean isFollowup; // có tái khám hay không

	@Future(message = "Ngày tái khám phải là ngày trong tương lai")
	private LocalDate followupDate;

	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;
	private String type;

	@Type(JsonBinaryType.class)
	@Column(columnDefinition = "jsonb")
	@Builder.Default
	private List<String> attachments = new ArrayList<>();
}
