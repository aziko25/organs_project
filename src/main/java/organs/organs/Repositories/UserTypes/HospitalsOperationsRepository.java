package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.HospitalsOperations;
import organs.organs.Models.UserTypes.Patients;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalsOperationsRepository extends JpaRepository<HospitalsOperations, Integer> {

    List<HospitalsOperations> findAllByHospitalId(Hospitals hospital);

    List<HospitalsOperations> findAllByPatientId(Patients patient);

    List<HospitalsOperations> findAllByDonorId(Donors donor);

    Optional<HospitalsOperations> findByIdAndHospitalId(Integer id, Hospitals hospitalId);

    Optional<HospitalsOperations> findByIdAndDonorId(Integer id, Donors donorId);
}