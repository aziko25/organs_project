package organs.organs.Repositories.OrgansAndQueues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.OrgansAndQueues.Organs;

@Repository
public interface OrgansRepository extends JpaRepository<Organs, Integer> {

    Organs findByName(String name);
}