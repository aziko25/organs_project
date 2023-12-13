package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.DonorsOrgans.DonorsOrgans;
import organs.organs.Models.ManyToMany.DonorsOrgans.DonorsOrgansId;

@Repository
public interface DonorsOrgansRepository extends JpaRepository<DonorsOrgans, DonorsOrgansId> {
}