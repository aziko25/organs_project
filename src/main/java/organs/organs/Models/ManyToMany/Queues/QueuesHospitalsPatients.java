package organs.organs.Models.ManyToMany.Queues;

import lombok.*;
import jakarta.persistence.*;
import organs.organs.Models.OrgansAndQueues.Queues;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.Patients;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "queues_hospitals_patients")
@IdClass(QueuesHospitalsPatientsId.class)
public class QueuesHospitalsPatients {

    @Id
    @ManyToOne
    @JoinColumn(name = "queue_id")
    private Queues queueId;

    @Id
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospitals hospitalId;

    @Id
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patientId;
}