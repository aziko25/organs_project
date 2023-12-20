package organs.organs.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.Regions;

@Repository
public interface RegionsRepository extends JpaRepository<Regions, Integer> {
}
