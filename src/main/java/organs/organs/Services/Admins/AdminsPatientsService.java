package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class AdminsPatientsService {

    private final UsersRepository usersRepository;
    private final PatientsRepository patientsRepository;
    private final OrgansRepository organsRepository;

    public List<Patients> allPatients() {

        return patientsRepository.findAll(Sort.by("id"));
    }

    public String createPatient(String fullName, String email, String password) {

        Optional<Users> userExists = usersRepository.findByEmail(email);

        if (userExists.isPresent()) {

            throw new IllegalArgumentException("Email Already Exists!");
        }

        Users user = new Users();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole("PATIENT");

        usersRepository.save(user);

        Patients patient = new Patients();

        patient.setUserId(user);

        patientsRepository.save(patient);

        return "You Successfully Created Patient!";
    }

    public String updatePatientsInfo(int patientId, String address, String city,
                                   LocalDate birthday, String bloodType,
                                   String district, String rhFactor, String comments) {

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found!"));

        if (address != null) {
            patient.setAddress(address);
        }

        if (city != null) {
            patient.setCity(city);
        }

        if (birthday != null) {
            patient.setBirthday(birthday);
        }

        if (bloodType != null) {
            patient.setBloodType(bloodType);
        }

        if (district != null) {
            patient.setDistrict(district);
        }

        if (rhFactor != null) {
            patient.setRhFactor(rhFactor);
        }

        if (comments != null) {
            patient.setComments(comments);
        }

        patientsRepository.save(patient);

        return "You Successfully Updated Patients Medical Card!";
    }
}