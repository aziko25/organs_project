package organs.organs.Models.UserTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "patients")
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private Users userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;
    private LocalDate birthday;
    private String city;
    private String district;

    @Column(name = "last_received")
    private LocalDate lastReceived;

    @Column(name = "passport_number", unique = true)
    private String passportNumber;

    @Column(unique = true)
    private String pinfl;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "rh_factor")
    private String rhFactor;

    @Column(name = "is_smoker")
    private Boolean isSmoker;

    @Column(name = "is_drinker")
    private Boolean isDrinker;

    @ManyToOne
    @JoinColumn(name = "organ_receives")
    private Organs organReceives;

    @ManyToMany(mappedBy = "patients")
    @JsonBackReference
    private Set<Hospitals> hospitals;

    @Column(name = "urgency_rate")
    private Integer urgencyRate;
    private String diagnosis;
    private String comments;
}