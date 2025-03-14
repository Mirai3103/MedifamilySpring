package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalDocument {
    public enum DocumentType {
        PRESCRIPTION,
        REPORT,
        CERTIFICATE,
        OTHER
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private FamilyMember member;

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

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
}