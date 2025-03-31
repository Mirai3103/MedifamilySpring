package sgu.j2ee.medifamily.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import sgu.j2ee.medifamily.entities.User;

@Data
@Builder
public class LoginResponse {
    private AuthenticationResponse token;
    private User user;
}
