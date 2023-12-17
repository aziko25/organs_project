package organs.organs.Models.ManyToMany.DonorsOrgans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Donors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "donors_organs")
@IdClass(DonorsOrgansId.class)
public class DonorsOrgans {

    @Id
    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organs organId;

    @Id
    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donors donorId;

    private Double price;
}