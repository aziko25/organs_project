package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Users;

import java.util.Optional;

@Repository
public interface DonorsRepository extends JpaRepository<Donors, Integer> {

    Optional<Donors> findByUserId(Users user);
}