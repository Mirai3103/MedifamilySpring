package sgu.j2ee.medifamily.dtos;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String code;
	private String message;
}
