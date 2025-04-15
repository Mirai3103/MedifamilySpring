package sgu.j2ee.medifamily.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medical_documents")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class MedicalDocument {
	public enum DocumentType {
		PRESCRIPTION, REPORT, CERTIFICATE, OTHER
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Profile profile;
	@Column(name = "profile_id", insertable = false, updatable = false)
	private Long profileId;

	@ManyToOne
	@JoinColumn(name = "medical_record_id")
	private MedicalRecord medicalRecord;

	private String title;
	private String filePath;
	private String fileType;
	private Long fileSize;

	@Enumerated(EnumType.STRING)
	private DocumentType documentType;

	private LocalDate documentDate;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;
}
