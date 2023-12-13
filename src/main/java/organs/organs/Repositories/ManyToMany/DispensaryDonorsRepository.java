package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonorsId;

@Repository
public interface DispensaryDonorsRepository extends JpaRepository<DispensaryDonors, DispensaryDonorsId> {
}
