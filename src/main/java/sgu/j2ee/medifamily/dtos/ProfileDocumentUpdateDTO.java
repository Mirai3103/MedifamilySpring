package sgu.j2ee.medifamily.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ProfileDocumentUpdateDTO {
	private Long id;
	private String name;
	private String note;
	private List<String> removedFiles = new ArrayList<>();
	@JsonIgnore
	private List<MultipartFile> newFiles = new ArrayList<>();
}
