package organs.organs.Models.ManyToMany.HospitalsDonorsOrgans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "hospitals_donors_organs")
@IdClass(HospitalsDonorsOrgansId.class)
public class HospitalsDonorsOrgans {

    @Id
    @ManyToOne
    @JoinColumn(name = "hospital_id", unique = true)
    private Hospitals hospitalId;

    @Id
    @ManyToOne
    @JoinColumn(name = "donor_id", unique = true)
    private Donors donorId;

    @Id
    @ManyToOne
    @JoinColumn(name = "organ_id", unique = true)
    private Organs organId;

    private Double price;
}