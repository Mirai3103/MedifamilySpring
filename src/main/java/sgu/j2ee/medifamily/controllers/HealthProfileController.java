package sgu.j2ee.medifamily.controllers;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.user.UpdateHealthProfile;
import sgu.j2ee.medifamily.entities.HealthProfile;
import sgu.j2ee.medifamily.exceptions.RequireLoginException;
import sgu.j2ee.medifamily.services.HealthProfileService;

@RestController
@RequestMapping("/api/health-profile")
@RequiredArgsConstructor
public class HealthProfileController {
	private final HealthProfileService healthProfileService;
	private final AuditorAware<String> auditorAware;

	@PutMapping("@me")
	public ResponseEntity<HealthProfile> updateMyHealthProfile(@RequestBody UpdateHealthProfile healthProfile) {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(() -> new RequireLoginException());
		healthProfile.setId(Long.parseLong(currentUserId));
		return ResponseEntity.ok(healthProfileService.updateHealthProfile(healthProfile));
	}

	@PutMapping("{id}")
	public ResponseEntity<HealthProfile> updateHealthProfile(@RequestBody UpdateHealthProfile healthProfile,
			@PathVariable String id) {
		healthProfile.setId(Long.parseLong(id));
		return ResponseEntity.ok(healthProfileService.updateHealthProfile(healthProfile));
	}

	@GetMapping("{id}")
	public ResponseEntity<HealthProfile> getHealthProfile(@PathVariable String id) {
		return ResponseEntity.ok(healthProfileService.getHealthProfileById(Long.parseLong(id)));
	}

	@GetMapping("@me")
	public ResponseEntity<HealthProfile> getMyHealthProfile() {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(() -> new RequireLoginException());
		return ResponseEntity.ok(healthProfileService.getHealthProfileById(Long.parseLong(currentUserId)));
	}

}
