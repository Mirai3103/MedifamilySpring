package sgu.j2ee.medifamily.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.ProfileDocumentDto;
import sgu.j2ee.medifamily.dtos.ProfileDocumentUpdateDTO;
import sgu.j2ee.medifamily.entities.ProfileDocument;
import sgu.j2ee.medifamily.mappers.ProfileDocumentMapper;
import sgu.j2ee.medifamily.services.ProfileDocumentService;

@RestController
@RequestMapping("/api/profile-documents")
@RequiredArgsConstructor
public class ProfileDocumentController {

	private final ProfileDocumentService profileDocumentService;
	private final ProfileDocumentMapper profileDocumentMapper;

	@GetMapping("/{profileId}")
	public ResponseEntity<List<ProfileDocumentDto>> getDocuments(@PathVariable Long profileId) {
		var documents = profileDocumentService.getProfileDocumentsByProfileId(profileId);
		return ResponseEntity.ok(profileDocumentMapper.toDto(documents));
	}

	@PostMapping(consumes = { "multipart/form-data" })
	public ResponseEntity<ProfileDocumentDto> createDocument(
			@RequestParam("name") String name,
			@RequestParam("note") String note,
			@RequestParam("profileId") Long profileId,
			@RequestParam("files") List<MultipartFile> files) throws IOException {
		var profileDocumentDto = new ProfileDocumentDto();
		profileDocumentDto.setName(name);
		profileDocumentDto.setNote(note);
		profileDocumentDto.setProfileId(profileId);
		profileDocumentDto.setFiles(files);
		var document = profileDocumentService.saveProfileDocument(profileDocumentDto);
		return ResponseEntity.ok(profileDocumentMapper.toDto(document));
	}

	@PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
	public ResponseEntity<ProfileDocument> updateDocument(
			@PathVariable Long id,
			@RequestParam("name") String name,
			@RequestParam("note") String note,
			@RequestParam("removeFiles") List<String> removeFiles,
			@RequestParam(value = "newFiles", required = false) List<MultipartFile> newFiles) throws IOException {
		var dto = new ProfileDocumentUpdateDTO();
		dto.setId(id);
		dto.setName(name);
		dto.setNote(note);
		dto.setRemovedFiles(removeFiles);
		if (newFiles != null) {
			dto.setNewFiles(newFiles);
		}
		var document = profileDocumentService.updateProfileDocument(dto);
		return ResponseEntity.ok(document);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
		profileDocumentService.deleteProfileDocument(id);
		return ResponseEntity.noContent().build();
	}
}
