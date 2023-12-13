package organs.organs.Models.ManyToMany.HospitalsDonorsOrgans;

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
public class HospitalsDonorsOrgansId implements Serializable {

    private Integer hospitalId;
    private Integer donorId;
    private Integer organId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        HospitalsDonorsOrgansId that = (HospitalsDonorsOrgansId) o;

        return Objects.equals(hospitalId, that.hospitalId) &&
                Objects.equals(donorId, that.donorId) &&
                Objects.equals(organId, that.organId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hospitalId, donorId, organId);
    }
}