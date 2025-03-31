package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Thành viên gia đình không được để trống")
    private FamilyMember member;

    @Size(max = 255, message = "Đường dẫn avatar không được vượt quá 255 ký tự")
    private String avatar;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}