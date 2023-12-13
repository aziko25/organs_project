package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgans;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgansId;
import organs.organs.Models.UserTypes.Hospitals;

import java.util.List;

@Repository
public interface HospitalsDonorsOrgansRepository extends JpaRepository<HospitalsDonorsOrgans, HospitalsDonorsOrgansId> {

    List<HospitalsDonorsOrgans> findAllByHospitalId(Hospitals hospital);
}