package organs.organs.Repositories.UserTypes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.Regions;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalsRepository extends JpaRepository<Hospitals, Integer> {

    Optional<Hospitals> findByName(String name);
    Optional<Hospitals> findByCreatorId(Users user);

    List<Hospitals> findAllBySpecializationOrgansAndCreatorId_RegionId(Organs specializationOrgans, Regions creatorId_regionId);

    Optional<Hospitals> findByIdAndSpecializationOrgans(Integer id, Organs specializationOrgans);
}