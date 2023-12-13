package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Hospitals;

@Repository
public interface HospitalsRepository extends JpaRepository<Hospitals, Integer> {
}