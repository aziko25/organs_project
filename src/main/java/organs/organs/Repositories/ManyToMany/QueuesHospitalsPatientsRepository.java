package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatientsId;
import organs.organs.Models.UserTypes.Hospitals;

import java.util.List;

@Repository
public interface QueuesHospitalsPatientsRepository extends JpaRepository<QueuesHospitalsPatients, QueuesHospitalsPatientsId> {

    List<QueuesHospitalsPatients> findAllByHospitalId(Hospitals hospital);
}