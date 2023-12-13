package organs.organs.Models.ManyToMany.Dispensary;

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
public class DispensaryDonorsId implements Serializable {

    private Integer dispensaryId;
    private Integer donorId;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DispensaryDonorsId that = (DispensaryDonorsId) o;

        return Objects.equals(dispensaryId, that.dispensaryId) &&
                Objects.equals(donorId, that.donorId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dispensaryId, donorId);
    }
}