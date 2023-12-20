package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Doctors;
import organs.organs.Models.UserTypes.Hospitals;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors, Integer> {

    List<Doctors> findAllByHospitalId(Hospitals hospital);

    Optional<Doctors> findByIdAndHospitalId(int id, Hospitals hospital);
}