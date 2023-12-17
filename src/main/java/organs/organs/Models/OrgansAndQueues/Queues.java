package organs.organs.Models.OrgansAndQueues;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.UserTypes.Hospitals;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "queues")
public class Queues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "organ_id")
    private Organs organId;

    @OneToOne
    @JoinColumn(name = "hospital_id", unique = true)
    private Hospitals hospitalId;
}