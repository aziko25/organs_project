package organs.organs.Models.ManyToMany.Queues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueuesHospitalsPatientsId implements Serializable {

    private Integer queueId;
    private Integer hospitalId;
    private Integer patientId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QueuesHospitalsPatientsId that = (QueuesHospitalsPatientsId) o;

        return Objects.equals(queueId, that.queueId) &&
                Objects.equals(hospitalId, that.hospitalId) &&
                Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(queueId, hospitalId, patientId);
    }
}