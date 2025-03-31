package sgu.j2ee.medifamily.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private LocalDateTime expiresAt;
}
