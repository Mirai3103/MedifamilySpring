package sgu.j2ee.medifamily.dtos.family;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMemberDTO {
	private Long id;
	private ProfileDTO profile;
	private String relationship;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private Long profileId;
	private Long familyId;
}
