package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;
import sgu.j2ee.medifamily.entities.MedicalDocument;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.MedicalDocument}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MedicalDocumentDto implements Serializable {
	private Long id;
	private ProfileDTO profile;
	private Long profileId;
	private MedicalRecordDto medicalRecord;
	private String title;
	private String filePath;
	private String fileType;
	private Long fileSize;
	private MedicalDocument.DocumentType documentType;
	private LocalDate documentDate;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String createdBy;
}