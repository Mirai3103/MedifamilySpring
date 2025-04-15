package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import sgu.j2ee.medifamily.dtos.family.FamilyDTO;
import sgu.j2ee.medifamily.dtos.family.FamilyMemberDTO;
import sgu.j2ee.medifamily.entities.ShareProfile;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.ShareProfile}
 */
@Data
public class ShareProfileDto implements Serializable {
	private UUID id = UUID.randomUUID();
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private FamilyDTO family;
	private Long familyId;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private FamilyMemberDTO member;
	private Long memberId;
	private List<SharePermissionDto> sharePermissions;
	private List<String> invitedEmails = new ArrayList<>();
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String shareToken;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private ShareProfile.ShareType shareType;
	private String reason;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private LocalDateTime updatedAt;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String createdBy;
}