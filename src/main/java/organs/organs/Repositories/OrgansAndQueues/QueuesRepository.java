package organs.organs.Repositories.OrgansAndQueues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.OrgansAndQueues.Queues;
import organs.organs.Models.UserTypes.Hospitals;

@Repository
public interface QueuesRepository extends JpaRepository<Queues, Integer> {

    Queues findByHospitalId(Hospitals hospitalId);
}