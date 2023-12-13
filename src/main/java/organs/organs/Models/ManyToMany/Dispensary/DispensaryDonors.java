package organs.organs.Models.ManyToMany.Dispensary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dispensary_donors")
@IdClass(DispensaryDonorsId.class)
public class DispensaryDonors {

    @Id
    @ManyToOne
    @JoinColumn(name = "dispensary_id")
    private Dispensary dispensaryId;

    @Id
    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donors donorId;

    private LocalDateTime date;
}