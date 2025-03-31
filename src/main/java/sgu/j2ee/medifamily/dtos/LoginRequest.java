package sgu.j2ee.medifamily.dtos;

import lombok.Data;

@Data
public class LoginRequest {
	private String username;
	private String password;
}
