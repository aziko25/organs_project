package organs.organs.Models.ManyToMany.Dispensary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DispensaryPatientsId implements Serializable {

    private Integer dispensaryId;
    private Integer patientId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DispensaryPatientsId that = (DispensaryPatientsId) o;

        return Objects.equals(dispensaryId, that.dispensaryId) &&
                Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dispensaryId, patientId);
    }
}