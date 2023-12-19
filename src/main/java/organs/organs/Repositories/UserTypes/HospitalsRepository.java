package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Models.UserTypes.Users;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface HospitalsRepository extends JpaRepository<Hospitals, Integer> {

    Optional<Hospitals> findByName(String name);
    Optional<Hospitals> findByCreatorId(Users user);

    List<Hospitals> findAllBySpecializationOrgans(Organs specializationOrgans);

    Optional<Hospitals> findByIdAndSpecializationOrgans(Integer id, Organs specializationOrgans);

    List<Hospitals> findAllByPatients(Set<Patients> patients);
}