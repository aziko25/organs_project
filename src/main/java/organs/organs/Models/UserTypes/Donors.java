package organs.organs.Models.UserTypes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donors")
public class Donors {

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

    @Column(name = "last_donated")
    private LocalDate lastDonated;

    @Column(name = "passport_number", unique = true)
    private String passportNumber;

    @Column(unique = true)
    private String pinfl;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "blood_type")
    private String bloodType;

    @Column(name = "is_smoker")
    private Boolean isSmoker;

    @Column(name = "is_drinker")
    private Boolean isDrinker;

    @ManyToOne
    @JoinColumn(name = "organ_donates")
    private Organs organDonates;
}