package sgu.j2ee.medifamily.dtos.family;

import java.lang.reflect.Member;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class AddMemberToFamilyRequest {

    private MemberProfile memberProfile;
    private boolean isHouseholder = false;
    @NotBlank(message = "Quan hệ với chủ hộ không được để trống")
    private String relationship;
    @NotNull(message = "Gia đình không được để trống")
    private Long familyId;
    private boolean isHasAccount = false;
    private Long accountId;

    @AssertTrue(message = "Thông tin thành viên không được để trống")
    private boolean isMemberInfoValid() {
        return (isHasAccount ) || memberProfile != null;
    }
}
