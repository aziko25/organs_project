package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonorsId;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;

import java.util.List;

@Repository
public interface DispensaryDonorsRepository extends JpaRepository<DispensaryDonors, DispensaryDonorsId> {

    List<DispensaryDonors> findAllByDonorIdAndDispensaryId(Donors donor, Dispensary dispensary);
    List<DispensaryDonors> findAllByDonorId(Donors donor);
}