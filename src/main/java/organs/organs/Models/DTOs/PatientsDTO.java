package organs.organs.Models.DTOs;

import lombok.Getter;
import lombok.Setter;
import organs.organs.Models.Regions;
import organs.organs.Models.UserTypes.Patients;

@Getter
@Setter
public class PatientsDTO {

    private Integer patientId;
    private String patientName;
    private Regions regionName;

    public PatientsDTO(Patients patient) {

        this.patientId = patient.getId();
        this.patientName = patient.getUserId().getFullName();
        this.regionName = patient.getUserId().getRegionId();
    }
}