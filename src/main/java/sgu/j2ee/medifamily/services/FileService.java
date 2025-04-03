package sgu.j2ee.medifamily.services;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.FileDocument;
import sgu.j2ee.medifamily.exceptions.NotFoundException;
import sgu.j2ee.medifamily.repositories.FileRepository;
import sgu.j2ee.medifamily.utils.FileContentStore;

@Service
@RequiredArgsConstructor
public class FileService {
	private final FileRepository fileRepository;

	private final FileContentStore contentStore;

	public FileDocument uploadFile(MultipartFile file) throws IOException {
		FileDocument document = new FileDocument();
		document.setName(file.getOriginalFilename());
		document.setContentMimeType(file.getContentType());
		document.setContentLength(file.getSize());
		document = fileRepository.save(document);

		contentStore.setContent(document, file.getInputStream());
		return fileRepository.save(document);
	}

	public FileDocument getFile(UUID id) {

		return fileRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("File not found"));
	}

	public void deleteFile(UUID id) {
		FileDocument document = fileRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		contentStore.unsetContent(document);
		fileRepository.delete(document);

	}

	public void deleteFile(String path) {
		UUID id = FileDocument.extractId(path);
		if (id != null)
			deleteFile(id);
	}
}
