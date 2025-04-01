package sgu.j2ee.medifamily.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import sgu.j2ee.medifamily.entities.FileDocument;
import sgu.j2ee.medifamily.services.FileService;
import sgu.j2ee.medifamily.utils.FileContentStore;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;
	private final FileContentStore contentStore;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileDocument> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		return ResponseEntity.ok(fileService.uploadFile(file));
	}

	@GetMapping("/view/{id}/{filename:.+}")
	public ResponseEntity<InputStreamResource> viewFile(@PathVariable UUID id,
			@PathVariable(required = false) String filename) {
		FileDocument document = fileService.getFile(id);

		InputStream stream = contentStore.getContent(document);

		// Trả về để hiển thị trực tiếp trong trình duyệt
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(document.getContentMimeType()))
				.contentLength(document.getContentLength())
				.body(new InputStreamResource(stream));
	}

	@GetMapping("/download/{id}/{filename:.+}")
	public ResponseEntity<InputStreamResource> downloadFile(@PathVariable UUID id,
			@PathVariable(required = false) String filename) {
		FileDocument document = fileService.getFile(id);

		InputStream stream = contentStore.getContent(document);

		// Trả về để download với tên gốc
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(document.getContentMimeType()))
				.contentLength(document.getContentLength())
				.header("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"")
				.body(new InputStreamResource(stream));
	}

	@DeleteMapping("/{id}/{filename:.+}")
	public ResponseEntity<Void> deleteFile(@PathVariable UUID id,
			@PathVariable(required = false) String filename) {
		fileService.deleteFile(id);
		return ResponseEntity.noContent().build();
	}
}