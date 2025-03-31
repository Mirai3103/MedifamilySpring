package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vaccinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccination {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private FamilyMember member;

	private String vaccineName;
	private LocalDateTime vaccinationDate;
	private String dose;
	private String batchNumber;
	private String location;
	private String reactions;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;
}
