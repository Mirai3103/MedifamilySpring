package sgu.j2ee.medifamily.dtos;

import java.time.LocalDateTime;

import org.springdoc.core.annotations.ParameterObject;

import lombok.Data;

@Data
@ParameterObject
public class VaccinationFilterDTO {
	private Long profileId;
	private String keyword; // sẽ apply vào vaccineName || batchNumber || location
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private String sortBy = "vaccinationDate";
	private String sortDirection = "desc"; // or "asc"
}
