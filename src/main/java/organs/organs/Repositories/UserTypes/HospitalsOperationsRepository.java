package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.HospitalsOperations;

import java.util.List;

@Repository
public interface HospitalsOperationsRepository extends JpaRepository<HospitalsOperations, Integer> {

    List<HospitalsOperations> findAllByHospitalId(Hospitals hospital);
}