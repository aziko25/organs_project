package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.UserTypes.HospitalsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.util.List;
import java.util.Optional;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class AdminsHospitalsService {

    private final UsersRepository usersRepository;
    private final HospitalsRepository hospitalsRepository;
    private final OrgansRepository organsRepository;

    public List<Hospitals> allHospitals() {

        return hospitalsRepository.findAll(Sort.by("id"));
    }

    public String createHospital(String name, int specializedOrganId, String address) {

        Organs organ = organsRepository.findById(specializedOrganId).orElseThrow(() -> new IllegalArgumentException("Organ Not Found"));

        Optional<Hospitals> hospitalExists = hospitalsRepository.findByName(name);

        if (hospitalExists.isPresent()) {

            throw new IllegalArgumentException("Hospital With This Name Already Exists!");
        }

        Hospitals hospital = new Hospitals();

        hospital.setName(name);
        hospital.setAddress(address);
        hospital.setSpecializationOrgans(organ);
        hospital.setCreatorId(USER);

        hospitalsRepository.save(hospital);

        return "You Successfully Created Hospital!";
    }

    public String updateHospital(int hospitalId, String name, String address) {

        Hospitals hospital = hospitalsRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("Hospital Not Found"));

        if (name != null) {

            hospital.setName(name);
        }

        if (address != null) {

            hospital.setAddress(address);
        }

        hospitalsRepository.save(hospital);

        return "You Successfully Updated Hospital!";
    }
}