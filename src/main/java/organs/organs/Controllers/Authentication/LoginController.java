package organs.organs.Controllers.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Configurations.Mail.EmailService;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.UsersRepository;
import organs.organs.Services.Authentication.LoginService;

import java.util.HashMap;
import java.util.Random;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class LoginController {

    private final LoginService loginService;
    private final UsersRepository usersRepository;
    private final EmailService emailService;

    public static HashMap<String, String> mapOfMailAndCodes = new HashMap<>();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {

        return new ResponseEntity<>(loginService.login(email, password), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {

        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Not Found!"));

        String code = generateUniqueCode();

        emailService.sendCodeToEmail(user.getEmail(), "Reset Password", "Your Reset Password Code: " + code);

        mapOfMailAndCodes.put(user.getEmail(), code);

        return ResponseEntity.ok("Reset Code Was Sent To Your Email!");
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<?> verifyCodeAndResetPassword(@RequestParam String email,
                                                        @RequestParam String code) {

        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Not Found!"));

        String correctCode = mapOfMailAndCodes.get(user.getEmail());

        if (correctCode != null && correctCode.equals(code)) {

            return new ResponseEntity<>("", HttpStatus.OK);
        }
        else {

            return new ResponseEntity<>("Invalid Code", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/enterNewPassword")
    public ResponseEntity<?> enterNewPassword(String email, String newPassword) {

        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Not Found!"));

        user.setPassword(newPassword);

        usersRepository.save(user);

        return new ResponseEntity<>("You Successfully Reset Your Password!", HttpStatus.OK);
    }

    private String generateUniqueCode() {

        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();

        while (builder.length() < 5) {

            int index = (int) (rnd.nextFloat() * ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return builder.toString();
    }
}