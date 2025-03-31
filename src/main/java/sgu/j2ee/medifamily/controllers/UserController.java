package sgu.j2ee.medifamily.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateProfileRequest;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	@GetMapping("/@me")
	public ResponseEntity<User> me() {
		return ResponseEntity.ok(userDetailsServiceImpl.getCurrentUser());
	}

	@PutMapping("@me")
	public ResponseEntity<User> updateMe(@RequestBody UpdateProfileRequest user) {
		return ResponseEntity.ok(userDetailsServiceImpl.updateUserProfile(user));
	}
}
