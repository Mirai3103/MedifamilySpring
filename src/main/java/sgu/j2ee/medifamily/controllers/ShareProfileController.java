package sgu.j2ee.medifamily.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.ShareProfileDto;
import sgu.j2ee.medifamily.entities.ShareProfile;
import sgu.j2ee.medifamily.mappers.ShareProfileMapper;
import sgu.j2ee.medifamily.repositories.SharePermissionRepository;
import sgu.j2ee.medifamily.repositories.ShareProfileRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-profile")
public class ShareProfileController {
	private final SharePermissionRepository sharePermissionRepository;
	private final ShareProfileRepository shareProfileRepository;
	private final ShareProfileMapper shareProfileMapper;

	@PostMapping("")
	public ResponseEntity<ShareProfile> createShareProfile(ShareProfileDto shareProfile) {
		return ResponseEntity.ok(shareProfileRepository.save(shareProfileMapper.toEntity(shareProfile)));
	}

	@GetMapping("{id}")
	public ResponseEntity<ShareProfileDto> getShareProfile(@PathVariable UUID id) {
		return shareProfileRepository.findById(id)
				.map(shareProfileMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
