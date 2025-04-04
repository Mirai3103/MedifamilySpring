package sgu.j2ee.medifamily.dtos;

import lombok.Builder;
import lombok.Data;
import sgu.j2ee.medifamily.dtos.user.UserDTO;

@Data
@Builder
public class LoginResponse {
	private AuthenticationResponse token;
	private UserDTO user;
}
