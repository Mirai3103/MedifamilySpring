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
import sgu.j2ee.medifamily.dtos.user.*;
import sgu.j2ee.medifamily.exceptions.UnAuthorizedException;
import sgu.j2ee.medifamily.mappers.IProfileMapper;
import sgu.j2ee.medifamily.mappers.IUserMapper;
import sgu.j2ee.medifamily.services.ProfileService;
import sgu.j2ee.medifamily.services.UserDetailsServiceImpl;

@RestController(value = "profilezController")
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final AuditorAware<String> auditorAware;
	private final IUserMapper userMapper;
	private final IProfileMapper profileMapper;

	@GetMapping("/@me")
	public ResponseEntity<UserDTO> me() {
		var currentUser = userDetailsServiceImpl.getCurrentUser();
		currentUser.getProfile().setUser(null);
		currentUser.getProfile().setFamilyMembers(null);
		return ResponseEntity.ok(userMapper.toDTO(currentUser));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
		var profile = profileService.getProfileById(id);
		return ResponseEntity.ok(profileMapper.toDTO(profile));
	}

	@PatchMapping("@me")
	public ResponseEntity<ProfileDTO> updateMe(@RequestBody @Valid UpdateProfileRequest user) {
		return ResponseEntity.ok(profileMapper.toDTO(profileService.updateUserBasicProfile(user)));
	}

	@PatchMapping(value = "@me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ProfileDTO> updateMyAvatar(@RequestParam("file") MultipartFile file) throws IOException {
		return ResponseEntity.ok(profileMapper.toDTO(profileService.updateMyAvatar(file)));
	}

	@PatchMapping(value = "@me/password")
	public ResponseEntity<UserDTO> updateMyPassword(@RequestBody @Valid UpdatePasswordRequest newPassword) {
		return ResponseEntity.ok(userMapper.toDTO(userDetailsServiceImpl.updateMyPassword(newPassword)));
	}

	@PatchMapping("/@me/health")
	public ResponseEntity<ProfileDTO> updateMyHealthProfile(@RequestBody UpdateHealthProfile healthProfile) {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(UnAuthorizedException::new);
		healthProfile.setId(Long.parseLong(currentUserId));
		return ResponseEntity.ok(profileMapper.toDTO(profileService.updateHealthProfile(healthProfile)));
	}

	@PutMapping("/{id}/health")
	public ResponseEntity<ProfileDTO> updateHealthProfile(@RequestBody UpdateHealthProfile healthProfile,
			@PathVariable String id) {
		healthProfile.setId(Long.parseLong(id));
		return ResponseEntity.ok(profileMapper.toDTO(profileService.updateHealthProfile(healthProfile)));
	}

}
