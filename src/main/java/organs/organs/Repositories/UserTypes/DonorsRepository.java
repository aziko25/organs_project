package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Donors;

@Repository
public interface DonorsRepository extends JpaRepository<Donors, Integer> {
}
