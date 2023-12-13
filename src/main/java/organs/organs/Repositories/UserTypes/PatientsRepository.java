package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Patients;

@Repository
public interface PatientsRepository extends JpaRepository<Patients, Integer> {
}
