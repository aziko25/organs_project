package organs.organs.Controllers.Donors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Donors.DonorsService;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class DonorsController {

    private final DonorsService donorsService;

    @Authorization(requiredRoles = {"DONOR"})
    @PostMapping("/becomeDonor")
    public ResponseEntity<?> becomeDonor() {

        return new ResponseEntity<>(donorsService.becomeDonor(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @PostMapping("/applyToDispensary")
    public ResponseEntity<?> applyToDispensary(@RequestParam int dispensaryId, @RequestParam String phone) {

        return new ResponseEntity<>(donorsService.applyToDispensary(dispensaryId, phone), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @GetMapping("/allMyDispensaryVisits")
    public ResponseEntity<?> allMyDispensaryVisits() {

        return new ResponseEntity<>(donorsService.allMyDispensaryVisits(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @GetMapping("/allHospitalsMatchingMe")
    public ResponseEntity<?> allHospitalsMatchingMe() {

        return new ResponseEntity<>(donorsService.allHospitalsFilteredByMyDonatingOrgan(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @PostMapping("/applyToHospital")
    public ResponseEntity<?> applyToHospital(@RequestParam int hospitalId) {

        return new ResponseEntity<>(donorsService.applyToHospital(hospitalId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @PutMapping("/decideOperationEnrollment")
    public ResponseEntity<?> decideOperationEnrollment(@RequestParam int operationId, @RequestParam boolean decision) {

        return new ResponseEntity<>(donorsService.acceptOrRejectOperation(operationId, decision), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR"})
    @GetMapping("/allMyOperations")
    public ResponseEntity<?> allMyOperations() {

        return new ResponseEntity<>(donorsService.allMyOperations(), HttpStatus.OK);
    }
}