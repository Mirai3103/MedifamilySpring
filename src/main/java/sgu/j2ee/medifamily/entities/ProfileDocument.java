package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDocument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String note;

	@Type(JsonBinaryType.class)
	@Column(columnDefinition = "jsonb")
	@Builder.Default
	private List<String> attachments = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "profile_id", nullable = false)
	private Profile profile;
	@Column(name = "profile_id", insertable = false, updatable = false)
	private Long profileId;

	@CreatedDate
	private LocalDateTime createdAt;

}
