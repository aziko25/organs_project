package organs.organs.Models.UserTypes;

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
@Table(name = "hospitals_operations")
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

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "doctor_specialization")
    private String doctorSpecialization;

    @Column(name = "operation_time")
    private LocalDateTime operationTime;
}