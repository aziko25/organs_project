package organs.organs.Models.UserTypes;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "hospitals_operations")
public class HospitalsOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospitals hospitalId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patientId;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donors donorId;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organs organId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctors doctorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "operation_time")
    private LocalDateTime operationTime;

    @Column(name = "is_donor_accepted")
    private Boolean isDonorAccepted;

    @Column(name = "operation_is_successful")
    private Boolean operationIsSuccessful;
}