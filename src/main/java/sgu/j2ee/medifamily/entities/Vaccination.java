package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vaccinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

	@Column(name = "is_done", columnDefinition = "boolean default false")
	private boolean isDone = false;
	private LocalDateTime vaccinationDate;
	private String dose;
	private String batchNumber;
	private String location;
	private String reactions;
	private String notes;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;
}
