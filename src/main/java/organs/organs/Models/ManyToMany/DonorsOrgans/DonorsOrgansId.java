package organs.organs.Models.ManyToMany.DonorsOrgans;

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
public class DonorsOrgansId implements Serializable {

    private Integer organId;
    private Integer donorId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DonorsOrgansId that = (DonorsOrgansId) o;

        return Objects.equals(organId, that.organId) &&
                Objects.equals(donorId, that.donorId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(organId, donorId);
    }
}