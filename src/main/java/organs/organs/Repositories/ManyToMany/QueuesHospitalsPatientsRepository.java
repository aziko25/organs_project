package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatientsId;

@Repository
public interface QueuesHospitalsPatientsRepository extends JpaRepository<QueuesHospitalsPatients, QueuesHospitalsPatientsId> {
}