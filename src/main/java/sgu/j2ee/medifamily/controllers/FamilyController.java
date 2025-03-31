package sgu.j2ee.medifamily.controllers;

import java.util.List;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.family.CreateFamilyRequest;
import sgu.j2ee.medifamily.entities.Family;
import sgu.j2ee.medifamily.exceptions.RequireLoginException;
import sgu.j2ee.medifamily.services.FamilyService;

@RestController
@RequestMapping("/api/family")
@RequiredArgsConstructor
public class FamilyController {
	private final FamilyService familyService;
	private final AuditorAware<String> auditorAware;

	@GetMapping("/{id}")
	public ResponseEntity<Family> getFamily(@PathVariable String id) {
		return ResponseEntity.ok(familyService.getFamilyById(Long.parseLong(id)));
	}

	@GetMapping("/@me")
	public ResponseEntity<List<Family>> getMyFamily() {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow();
		return ResponseEntity.ok(familyService.getFamiliesByUserId(Long.parseLong(currentUserId)));
	}

	@PostMapping("")
	public ResponseEntity<Family> createFamily(@RequestBody CreateFamilyRequest family) {
		var currentUserId = auditorAware.getCurrentAuditor().orElseThrow(RequireLoginException::new);
		family.setCreatedBy(Long.parseLong(currentUserId));
		return ResponseEntity.ok(familyService.createFamily(family));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Family> updateFamily(
			@RequestBody CreateFamilyRequest family, @PathVariable String id) {
		return ResponseEntity.ok(familyService.updateFamily(Long.parseLong(id), family));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFamily(@PathVariable String id) {
		familyService.deleteFamily(Long.parseLong(id));
		return ResponseEntity.ok().build();
	}
}
