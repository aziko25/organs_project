package organs.organs.Services.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.UsersRepository;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UsersRepository usersRepository;

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

        return "You Successfully Signed Up!";
    }
}