package sgu.j2ee.medifamily.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.dtos.ProfileDocumentDto;
import sgu.j2ee.medifamily.dtos.ProfileDocumentUpdateDTO;
import sgu.j2ee.medifamily.entities.FileDocument;
import sgu.j2ee.medifamily.entities.ProfileDocument;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.ProfileDocumentRepository;
import sgu.j2ee.medifamily.repositories.ProfileRepository;

@Service
@RequiredArgsConstructor
public class ProfileDocumentService {
	private final ProfileDocumentRepository profileDocumentRepository;
	private final FileService fileService;
	private final ProfileRepository profileRepository;

	public void deleteProfileDocument(Long id) {
		profileDocumentRepository.findById(id).ifPresent(profileDocument -> {
			profileDocument.getAttachments().forEach(attachment -> {
				try {
					fileService.deleteFile(attachment);
				} catch (Exception ignored) {
				}
			});
			profileDocumentRepository.deleteById(id);
		});
	}

	public ProfileDocument saveProfileDocument(ProfileDocumentDto profileDocument) throws IOException {
		var listMultipartFile = fileService.uploadFile(profileDocument.getFiles());
		var attachments = listMultipartFile.stream().map(FileDocument::getServerPath).toList();
		var profile = profileRepository.findById(profileDocument.getProfileId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
		var document = ProfileDocument.builder()
				.attachments(attachments)
				.profile(profile)
				.profileId(profileDocument.getProfileId())
				.note(profileDocument.getNote())
				.name(profileDocument.getName())
				.build();
		return profileDocumentRepository.save(document);
	}

	public ProfileDocument updateProfileDocument(ProfileDocumentUpdateDTO profileDocument) throws IOException {
		var document = profileDocumentRepository.findById(profileDocument.getId()).orElseThrow();
		document.setName(profileDocument.getName());
		document.setNote(profileDocument.getNote());
		if (profileDocument.getNewFiles() != null) {
			var listMultipartFile = fileService.uploadFile(profileDocument.getNewFiles());
			var attachments = listMultipartFile.stream().map(FileDocument::getServerPath).toList();
			document.getAttachments().addAll(attachments);
		}
		if (profileDocument.getRemovedFiles() != null) {
			for (String file : profileDocument.getRemovedFiles()) {
				try {
					fileService.deleteFile(file);
				} catch (Exception ignored) {
				}
			}
			document.getAttachments().removeAll(profileDocument.getRemovedFiles());
		}
		return profileDocumentRepository.save(document);
	}

	public List<ProfileDocument> getProfileDocumentsByProfileId(Long profileId) {
		return profileDocumentRepository.findByProfileId(profileId);
	}
}
