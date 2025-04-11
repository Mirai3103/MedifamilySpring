package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;

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
	@JoinColumn(name = "profile_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Profile profile;
	@Column(name = "profile_id", insertable = false, updatable = false)
	private Long profileId;

	private String vaccineName;
	private LocalDateTime vaccinationDate;
	private String dose;
	private String batchNumber;
	private String location;
	private String reactions;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;
}
