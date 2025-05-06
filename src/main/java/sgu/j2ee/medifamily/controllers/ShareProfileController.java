package sgu.j2ee.medifamily.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.ShareProfileDto;
import sgu.j2ee.medifamily.dtos.ShareProfileQuery;
import sgu.j2ee.medifamily.mappers.ShareProfileMapper;
import sgu.j2ee.medifamily.services.ShareProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-profile")
public class ShareProfileController {
	private final ShareProfileService shareProfileService;
	private final ShareProfileMapper shareProfileMapper;

	@PostMapping("")
	public ResponseEntity<ShareProfileDto> createShareProfile(@RequestBody ShareProfileDto shareProfile) {

		return ResponseEntity.ok(
				shareProfileMapper.toDto(shareProfileService.shareProfile(shareProfileMapper.toEntity(shareProfile))));
	}

	@GetMapping("{id}")
	public ResponseEntity<ShareProfileDto> getShareProfile(@PathVariable UUID id) {
		return shareProfileService.getShareProfileById(id)
				.map(shareProfileMapper::toDto)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("")
	public ResponseEntity<List<ShareProfileDto>> getAllShareProfiles(ShareProfileQuery query) {
		return ResponseEntity.ok(shareProfileMapper.toDto(shareProfileService.getAllShareProfiles(query)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShareProfile(@PathVariable UUID id) {
		shareProfileService.deleteShareProfile(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/by-ids")
	public ResponseEntity<List<ShareProfileDto>> getShareProfilesByIds(@RequestParam List<UUID> ids) {
		return ResponseEntity.ok(shareProfileMapper.toDto(shareProfileService.getShareProfilesByIds(ids)));
	}

}
