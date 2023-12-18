package organs.organs.Models.ManyToMany.Dispensary;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(schema = "public", name = "dispensary_donors")
public class DispensaryDonors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "dispensary_id")
    private Dispensary dispensaryId;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donors donorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_processing")
    private Boolean isProcessing;
}