package organs.organs.Repositories.OrgansAndQueues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.OrgansAndQueues.Queues;

@Repository
public interface QueuesRepository extends JpaRepository<Queues, Integer> {
}