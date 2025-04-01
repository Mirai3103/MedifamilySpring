package sgu.j2ee.medifamily.controllers;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdatePasswordRequest;
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

	@PatchMapping("@me")
	public ResponseEntity<User> updateMe(@RequestBody @Valid UpdateProfileRequest user) {
		return ResponseEntity.ok(userDetailsServiceImpl.updateUserProfile(user));
	}

	@PatchMapping(value = "@me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<User> updateMyAvatar(@RequestParam("file") MultipartFile file) throws IOException {
		return ResponseEntity.ok(userDetailsServiceImpl.updateMyAvatar(file));
	}

	@PatchMapping(value = "@me/password")
	public ResponseEntity<User> updateMyPassword(@RequestBody @Valid UpdatePasswordRequest newPassword) {
		return ResponseEntity.ok(userDetailsServiceImpl.updateMyPassword(newPassword));
	}
}
