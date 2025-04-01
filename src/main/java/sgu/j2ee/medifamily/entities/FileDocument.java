package sgu.j2ee.medifamily.entities;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class FileDocument {

	@Id
	@Builder.Default
	private UUID id = UUID.randomUUID();
	private String name = "";
	private Date created = new Date();
	private String summary;
	@ContentId
	private String contentId;
	@ContentLength
	private long contentLength;
	private String contentMimeType = "text/plain";

	@JsonIgnore
	private LocalDateTime lastChecked = LocalDateTime.now();

	@Transient
	public String getServerPath() {
		return id.toString() + "/" + this.name;
	}

	public static UUID extractId(String serverPath) {
		String[] parts = serverPath.split("/");
		if (parts.length > 0) {
			try {
				return UUID.fromString(parts[0]);
			} catch (IllegalArgumentException e) {
				return null;
			}
		}
		return null;
	}
}