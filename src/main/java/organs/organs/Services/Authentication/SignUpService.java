package organs.organs.Services.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UsersRepository usersRepository;
    private final DonorsRepository donorsRepository;
    private final PatientsRepository patientsRepository;

    public String signUp(String fullName, String email, String password, String rePassword, String role) {

        if (!password.equals(rePassword)) {

            throw new IllegalArgumentException("Passwords Didn't Match!");
        }

        if (!role.equalsIgnoreCase("HOSPITAL") && !role.equalsIgnoreCase("PATIENT") &&
                !role.equalsIgnoreCase("DONOR") && !role.equalsIgnoreCase("DISPENSARY")) {

            throw new IllegalArgumentException("User Types Can Be Only Hospital Or Patient Or Donor Or Dispensary!");
        }

        if (usersRepository.findByEmail(email).isPresent()) {

            throw new IllegalArgumentException("Email Already Exists!");
        }

        Users user = new Users();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role.toUpperCase());

        usersRepository.save(user);

        if (role.equalsIgnoreCase("PATIENT")) {

            Patients patient = new Patients();

            patient.setUserId(user);

            patientsRepository.save(patient);
        }
        else if (role.equalsIgnoreCase("DONOR")) {

            Donors donor = new Donors();

            donor.setUserId(user);

            donorsRepository.save(donor);
        }

        return "You Successfully Signed Up!";
    }
}