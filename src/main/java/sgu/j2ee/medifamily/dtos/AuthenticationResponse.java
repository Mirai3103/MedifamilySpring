package sgu.j2ee.medifamily.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
	private String token;
	private LocalDateTime expiresAt;
}
