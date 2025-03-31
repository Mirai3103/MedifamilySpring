package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Thông tin thành viên gia đình không được để trống")
    private FamilyMember member;

    @NotNull(message = "Ngày khám không được để trống")
    @PastOrPresent(message = "Ngày khám phải là ngày hiện tại hoặc trong quá khứ")
    private LocalDate visitDate;

    @NotBlank(message = "Cơ sở y tế không được để trống")
    @Size(min = 2, max = 200, message = "Tên cơ sở y tế phải có từ 2 đến 200 ký tự")
    private String medicalFacility;

    @Size(min = 2, max = 100, message = "Tên bác sĩ phải có từ 2 đến 100 ký tự")
    private String doctorName;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @NotBlank(message = "Chẩn đoán không được để trống")
    @Size(min = 2, max = 500, message = "Chẩn đoán phải có từ 2 đến 500 ký tự")
    private String diagnosis;

    @Size(max = 1000, message = "Phương pháp điều trị không được vượt quá 1000 ký tự")
    private String treatment;

    @Size(max = 2000, message = "Ghi chú không được vượt quá 2000 ký tự")
    private String notes;

    private Boolean isFollowup;

    @Future(message = "Ngày tái khám phải là ngày trong tương lai")
    private LocalDate followupDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "Người tạo hồ sơ không được để trống")
    private User createdBy;
}