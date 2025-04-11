package sgu.j2ee.medifamily.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "share_permissions")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@Builder
public class SharePermission {
	public enum PermissionType {
		VIEW, EDIT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "share_request_id", nullable = false)
	private ShareRequest shareRequest;

	@Enumerated(EnumType.STRING)
	private PermissionType permissionType;

	private Boolean isGranted;
}
