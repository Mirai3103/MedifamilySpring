package sgu.j2ee.medifamily.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link sgu.j2ee.medifamily.entities.Vaccination}
 */
@Data
public class VaccinationDto implements Serializable {
    private Long id;
    private Long profileId;
    private String vaccineName;
    private LocalDateTime vaccinationDate;
    private String dose;
    private String batchNumber;
    private String location;
    private String reactions;
    private String notes;
}