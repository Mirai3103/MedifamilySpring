package sgu.j2ee.medifamily.dtos;

import org.springdoc.core.annotations.ParameterObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ParameterObject
public class DoctorSearchRequest {
	private Long userId;
	private String keyword;
}
