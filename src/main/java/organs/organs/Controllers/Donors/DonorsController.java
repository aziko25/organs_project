package organs.organs.Controllers.Donors;

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

    @Authorization(requiredRoles = {"DONOR", "APPROVED_DONOR"})
    @GetMapping("/myDonorInfo")
    public ResponseEntity<?> myDonorsInfo() {

        return new ResponseEntity<>(donorsService.myDonorInfo(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR", "APPROVED_DONOR"})
    @PostMapping("/applyToDispensary")
    public ResponseEntity<?> applyToDispensary(@RequestParam int dispensaryId, @RequestParam String phone) {

        return new ResponseEntity<>(donorsService.applyToDispensary(dispensaryId, phone), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DONOR", "APPROVED_DONOR"})
    @GetMapping("/allMyDispensaryVisits")
    public ResponseEntity<?> allMyDispensaryVisits() {

        return new ResponseEntity<>(donorsService.allMyDispensaryVisits(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_DONOR"})
    @GetMapping("/allHospitalsMatchingMe")
    public ResponseEntity<?> allHospitalsMatchingMe() {

        return new ResponseEntity<>(donorsService.allHospitalsFilteredByMyDonatingOrgan(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_DONOR"})
    @PostMapping("/applyToHospital")
    public ResponseEntity<?> applyToHospital(@RequestParam int hospitalId) {

        return new ResponseEntity<>(donorsService.applyToHospital(hospitalId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_DONOR"})
    @PutMapping("/decideOperationEnrollment")
    public ResponseEntity<?> decideOperationEnrollment(@RequestParam int operationId, @RequestParam boolean decision) {

        return new ResponseEntity<>(donorsService.acceptOrRejectOperation(operationId, decision), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_DONOR"})
    @GetMapping("/allMyOperations")
    public ResponseEntity<?> allMyOperations() {

        return new ResponseEntity<>(donorsService.allMyOperations(), HttpStatus.OK);
    }
}