package organs.organs.Controllers.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Repositories.UserTypes.UsersRepository;

import static organs.organs.Services.Authentication.LoginService.EMAIL;

@RestController
@RequestMapping("/api/me")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class MeController {

    private final UsersRepository usersRepository;

    @Authorization(requiredRoles = {"DISPENSARY", "HOSPITAL", "PATIENT", "DONOR"})
    @GetMapping
    public ResponseEntity<?> myInfo() {

        return new ResponseEntity<>(usersRepository.findByEmail(EMAIL), HttpStatus.OK);
    }
}