package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "medical_record_id", nullable = true)
	private MedicalRecord medicalRecord;
	@Column(name = "medical_record_id", insertable = false, updatable = false)
	private Long medicalRecordId;

	@LastModifiedBy
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "prescription_id")
	private List<PrescriptionItem> items;

	@CreatedBy
	private String createdBy;
}
