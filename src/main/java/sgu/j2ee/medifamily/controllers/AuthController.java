package sgu.j2ee.medifamily.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import sgu.j2ee.medifamily.dtos.LoginRequest;
import sgu.j2ee.medifamily.dtos.LoginResponse;
import sgu.j2ee.medifamily.dtos.RegisterRequest;
import sgu.j2ee.medifamily.dtos.ResetPasswordDTO;
import sgu.j2ee.medifamily.services.AuthService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController()
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	private final AuthService authService;
	private final UserDetailsServiceImpl userDetailsService;

	@PostMapping("/register")
	public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest user)
			throws URISyntaxException {
		return ResponseEntity.created(new URI("/api/users/" + authService.register(user).getId()))
				.build();
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		String username = request.getUsername();
		String password = request.getPassword();

		return ResponseEntity.ok(authService.login(username, password));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout() {
		return ResponseEntity.ok().build();
	}

	@PostMapping("/send-reset-password-email")
	public ResponseEntity<Void> sendResetPasswordEmail(@RequestBody String email) {
		authService.sendPasswordResetEmail(email.replace("\"", ""));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/verify-reset-password-token")
	public ResponseEntity<Map<String, String>> verifyResetPasswordToken(@RequestParam String token) {
		var email = authService.validatePasswordResetToken(token);
		return ResponseEntity.ok(Map.of("email", email));
	}

	@PatchMapping("/reset-password")
	public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO) {
		authService.resetPassword(resetPasswordDTO);
		return ResponseEntity.ok().build();
	}
}
