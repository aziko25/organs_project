package organs.organs.Controllers.Patients;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Patients.PatientsService;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class PatientsController {

    private final PatientsService patientsService;

    @Authorization(requiredRoles = {"PATIENT"})
    @PostMapping("/becomePatient")
    public ResponseEntity<?> becomePatient() {

        return new ResponseEntity<>(patientsService.becomePatient(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"PATIENT"})
    @PostMapping("/applyToDispensary")
    public ResponseEntity<?> applyToDispensary(@RequestParam int dispensaryId, @RequestParam String phone) {

        return new ResponseEntity<>(patientsService.applyToDispensary(dispensaryId, phone), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"PATIENT"})
    @GetMapping("/allMyDispensaryVisits")
    public ResponseEntity<?> allMyDispensaryVisits() {

        return new ResponseEntity<>(patientsService.allMyDispensaryVisits(), HttpStatus.OK);
    }
}