package organs.organs.Models.ManyToMany.Dispensary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Patients;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dispensary_patients")
@IdClass(DispensaryPatientsId.class)
public class DispensaryPatients {

    @Id
    @ManyToOne
    @JoinColumn(name = "dispensary_id")
    private Dispensary dispensaryId;

    @Id
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patientId;

    private LocalDateTime date;
}