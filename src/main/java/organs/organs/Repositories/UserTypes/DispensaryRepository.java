package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Dispensary;

@Repository
public interface DispensaryRepository extends JpaRepository<Dispensary, Integer> {
}
