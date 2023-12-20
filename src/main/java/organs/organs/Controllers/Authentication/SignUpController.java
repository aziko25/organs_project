package organs.organs.Controllers.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Services.Authentication.SignUpService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestParam String fullName, @RequestParam String email, @RequestParam String password,
                                    @RequestParam String rePassword, @RequestParam String role, @RequestParam int regionId) {

        return new ResponseEntity<>(signUpService.signUp(fullName, email, password, rePassword, role, regionId), HttpStatus.OK);
    }
}