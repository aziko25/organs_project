package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Users;

import java.util.Optional;

@Repository
public interface DispensaryRepository extends JpaRepository<Dispensary, Integer> {

    Optional<Dispensary> findByCreatorId(Users user);
}