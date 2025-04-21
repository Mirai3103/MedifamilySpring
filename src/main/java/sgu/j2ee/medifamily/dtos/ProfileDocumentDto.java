package sgu.j2ee.medifamily.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.ProfileDocument}
 */
@Data
public class ProfileDocumentDto implements Serializable {
	private Long id;
	private String name;
	private String note;
	private List<String> attachments = new ArrayList<>();
	@JsonIgnore
	private List<MultipartFile> files = new ArrayList<>();
	private Long profileId;
	private LocalDateTime createdAt;
}