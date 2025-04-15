package sgu.j2ee.medifamily.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "share_permissions")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class SharePermission {
	public enum PermissionType {
		VIEW, EDIT, CREATE
	}

	public enum ResourceType {
		PROFILE, VACCINATION, MEDICAL_RECORD, FILE_DOCUMENT, PRESCRIPTION
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "share_profile_id", nullable = false)
	private ShareProfile shareRequest;
	@Column(name = "share_profile_id", insertable = false, updatable = false)
	private UUID shareRequestId;
	@Type(JsonBinaryType.class)
	@Column(columnDefinition = "jsonb")
	@Builder.Default
	private List<PermissionType> permissionTypes = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private ResourceType resourceType;

}
