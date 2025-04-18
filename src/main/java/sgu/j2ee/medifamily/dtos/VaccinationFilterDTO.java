package sgu.j2ee.medifamily.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VaccinationFilterDTO {
    private Long profileId;
    private String keyword; // sẽ apply vào vaccineName || batchNumber || location
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String sortBy = "vaccinationDate";
    private String sortDirection = "desc"; // or "asc"
}
