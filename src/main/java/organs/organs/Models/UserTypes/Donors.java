package organs.organs.Models.UserTypes;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.Regions;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "public", name = "donors")
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String city;
    private String district;

    @Column(name = "last_donated")
    private LocalDate lastDonated;

    @ManyToOne
    @JoinColumn(name = "organ_donates")
    private Organs organDonates;

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

    @Column(name = "donation_price")
    private Double donationPrice;

    private String comments;
}