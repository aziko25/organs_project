package organs.organs.Controllers.DBData;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import organs.organs.Models.DTOs.PatientsDTO;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.HospitalsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.util.*;

@RestController
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
@RequestMapping("/api/dbInfo")
public class DbDataInfoControllers {

    private final PatientsRepository patientsRepository;
    private final DonorsRepository donorsRepository;
    private final UsersRepository usersRepository;

    @GetMapping("/allPatients")
    public ResponseEntity<?> allPatients() {

        List<Map<String, Object>> mapList = new ArrayList<>();

        List<Patients> patientsList = patientsRepository.findAll();

        for (Patients patients : patientsList) {

            Map<String, Object> patientMap = new LinkedHashMap<>();

            patientMap.put("id", patients.getId());
            patientMap.put("name", patients.getUserId().getFullName());
            patientMap.put("regionName", patients.getUserId().getRegionId().getName());

            mapList.add(patientMap);
        }


        return ResponseEntity.ok(mapList);
    }

    @GetMapping("/allDonors")
    public ResponseEntity<?> allDonors() {

        List<Donors> donorsList = donorsRepository.findAll();

        List<Map<String, Object>> mapList = new ArrayList<>();

        for (Donors donor : donorsList) {

            Map<String, Object> donorMap = new LinkedHashMap<>();

            donorMap.put("id", donor.getId());
            donorMap.put("name", donor.getUserId().getFullName());
            donorMap.put("regionName", donor.getUserId().getRegionId().getName());

            mapList.add(donorMap);
        }

        return ResponseEntity.ok(mapList);
    }

    @GetMapping("/allHospitals")
    public ResponseEntity<?> allHospitals() {

        return ResponseEntity.ok(usersRepository.findAll());
    }
}