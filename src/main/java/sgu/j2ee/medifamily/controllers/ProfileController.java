package sgu.j2ee.medifamily.controllers;

import java.io.IOException;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateHealthProfile;
import sgu.j2ee.medifamily.dtos.user.UpdatePasswordRequest;
import sgu.j2ee.medifamily.dtos.user.UpdateProfileRequest;
import sgu.j2ee.medifamily.entities.Profile;
import sgu.j2ee.medifamily.entities.User;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.services.ProfileService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController(value = "profilezController")
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final AuditorAware<String> auditorAware;

	@GetMapping("/@me")
	public ResponseEntity<User> me() {
		var currentUser = userDetailsServiceImpl.getCurrentUser();
		currentUser.getProfile().setUser(null);
		return ResponseEntity.ok(currentUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
		var profile = profileService.getProfileById(id);
		profile.setUser(null);
		return ResponseEntity.ok(profile);
	}

	@PatchMapping("@me")
	public ResponseEntity<Profile> updateMe(@RequestBody @Valid UpdateProfileRequest user) {
		return ResponseEntity.ok(profileService.updateUserBasicProfile(user));
	}

	@PatchMapping(value = "@me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Profile> updateMyAvatar(@RequestParam("file") MultipartFile file) throws IOException {
		return ResponseEntity.ok(profileService.updateMyAvatar(file));
	}

	@PatchMapping(value = "@me/password")
	public ResponseEntity<User> updateMyPassword(@RequestBody @Valid UpdatePasswordRequest newPassword) {
		return ResponseEntity.ok(userDetailsServiceImpl.updateMyPassword(newPassword));
	}

	@PatchMapping("/@me/health")
	public ResponseEntity<Profile> updateMyHealthProfile(@RequestBody UpdateHealthProfile healthProfile) {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(() -> new UnAuthorizedException());
		healthProfile.setId(Long.parseLong(currentUserId));
		return ResponseEntity.ok(profileService.updateHealthProfile(healthProfile));
	}

	@PutMapping("/{id}/health")
	public ResponseEntity<Profile> updateHealthProfile(@RequestBody UpdateHealthProfile healthProfile,
			@PathVariable String id) {
		healthProfile.setId(Long.parseLong(id));
		return ResponseEntity.ok(profileService.updateHealthProfile(healthProfile));
	}

}
