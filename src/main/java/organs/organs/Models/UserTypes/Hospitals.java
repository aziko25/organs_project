package organs.organs.Models.UserTypes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hospitals")
public class Hospitals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "creator_id", unique = true)
    private Users creatorId;

    @Column(unique = true)
    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "specialization_organ")
    private Organs specializationOrgans;

    private String description;

    @Column(name = "image_link")
    private String imageLink;

    @ManyToMany
    @JoinTable(
            name = "hospitals_patients",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @JsonManagedReference
    private Set<Patients> patients;

    /*@ManyToMany
    @JoinTable(
            name = "hospitals_organs",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = @JoinColumn(name = "organ_id")
    )
    @JsonManagedReference
    private Set<Organs> organs;*/
}