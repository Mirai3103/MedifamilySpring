package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import sgu.j2ee.medifamily.entities.SharePermission;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.SharePermission}
 */
@Data
public class SharePermissionDto implements Serializable {
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long id;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private UUID shareRequestId;
	private List<SharePermission.PermissionType> permissionTypes = new ArrayList<>();
	private SharePermission.ResourceType resourceType;
}