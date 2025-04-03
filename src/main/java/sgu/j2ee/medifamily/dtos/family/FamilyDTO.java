package sgu.j2ee.medifamily.dtos.family;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.ProfileDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyDTO {
	private Long id;
	private String familyName;
	private String address;
	private ProfileDTO owner;
	private String phoneNumber;
	private String email;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private List<FamilyMemberDTO> familyMembers;
	private Long ownerId;
}
