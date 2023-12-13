package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgans;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgansId;

@Repository
public interface HospitalsDonorsOrgansRepository extends JpaRepository<HospitalsDonorsOrgans, HospitalsDonorsOrgansId> {
}