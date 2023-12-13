package organs.organs.Controllers.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import organs.organs.Services.Authentication.SignUpService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestParam String fullName, @RequestParam String email, @RequestParam String password,
                                    @RequestParam String rePassword, @RequestParam String role) {

        return new ResponseEntity<>(signUpService.signUp(fullName, email, password, rePassword, role), HttpStatus.OK);
    }
}