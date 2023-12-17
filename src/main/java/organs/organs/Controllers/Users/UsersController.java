package organs.organs.Controllers.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Users.UsersService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @Authorization(requiredRoles = {"ADMIN", "DONOR", "APPROVED_DONOR", "PATIENT", "APPROVED_PATIENT", "DISPENSARY", "HOSPITAL"})
    @GetMapping("/myInfo")
    public ResponseEntity<?> myInfo() {

        return ResponseEntity.ok(usersService.myInfo());
    }

    @Authorization(requiredRoles = {"ADMIN", "DONOR", "APPROVED_DONOR", "PATIENT", "APPROVED_PATIENT", "DISPENSARY", "HOSPITAL"})
    @PutMapping("/uploadMyPhoto")
    public ResponseEntity<?> uploadMyPhoto(@RequestParam(required = false, value = "image") MultipartFile photo) {

        return ResponseEntity.ok(usersService.userUploadPhoto(photo));
    }
}