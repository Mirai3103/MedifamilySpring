package sgu.j2ee.medifamily.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "share_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ShareProfile {

	public enum ShareType {
		AnyOneWithLink, OnlyInvitedPeople
	}

	@Id
	private UUID id = UUID.randomUUID();

	@ManyToOne
	@JoinColumn(name = "family_id", nullable = false)
	private Family family;
	@Column(name = "family_id", insertable = false, updatable = false)
	private Long familyId;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = true)
	private FamilyMember member;
	@Column(name = "member_id", insertable = false, updatable = false)
	private Long memberId;

	@OneToMany
	@JoinColumn(name = "share_profile_id")
	private List<SharePermission> sharePermissions;

	public boolean isShareAll() {
		return memberId == null;
	}

	@Type(JsonBinaryType.class)
	@Column(columnDefinition = "jsonb")
	@Builder.Default
	private List<String> invitedEmails = new ArrayList<>();

	private String shareToken;
	@CreatedDate
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;

	@Enumerated(EnumType.STRING)
	private ShareType shareType;

	private String reason;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	@CreatedBy
	private String createdBy;

}
