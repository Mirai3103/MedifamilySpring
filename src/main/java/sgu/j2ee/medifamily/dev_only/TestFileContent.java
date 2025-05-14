package sgu.j2ee.medifamily.dev_only;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sgu.j2ee.medifamily.entities.FileDocument;
import sgu.j2ee.medifamily.services.FileService;
import sgu.j2ee.medifamily.utils.FileContentStore;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class TestFileContent {
	private final FileContentStore fileContentStore;
	private final FileService fileService;

	@ShellMethod("Upload local file using FileContentStore directly")
	public String uploadWithContentStore(String path) {
		try {
			File file = new File(path);
			FileInputStream inputStream = new FileInputStream(file);
			String mimeType = Files.probeContentType(file.toPath());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			FileDocument document = new FileDocument();
			document.setName(file.getName());
			document.setContentMimeType(mimeType);
			document.setContentLength(file.length());

			// Save metadata trước (giống như repository.save)
			fileContentStore.setContent(document, inputStream);

			// In ra contentId sau khi upload
			String id = document.getContentId();
			document.getServerPath();
			return "Uploaded file using FileContentStore with contentId: " + id;
		} catch (Exception e) {
			log.error("Upload failed", e);
			return "Error: " + e.getMessage();
		}
	}

}
