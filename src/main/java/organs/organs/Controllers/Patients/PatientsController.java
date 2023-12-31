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

    @Authorization(requiredRoles = {"PATIENT", "APPROVED_PATIENT"})
    @GetMapping("/myInfo")
    public ResponseEntity<?> myPatientInfo() {

        return new ResponseEntity<>(patientsService.myPatientInfo(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"PATIENT", "APPROVED_PATIENT"})
    @PostMapping("/applyToDispensary")
    public ResponseEntity<?> applyToDispensary(@RequestParam int dispensaryId, @RequestParam String phone) {

        return new ResponseEntity<>(patientsService.applyToDispensary(dispensaryId, phone), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"PATIENT", "APPROVED_PATIENT"})
    @GetMapping("/allMyDispensaryVisits")
    public ResponseEntity<?> allMyDispensaryVisits() {

        return new ResponseEntity<>(patientsService.allMyDispensaryVisits(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @GetMapping("/allHospitalsMatchingMe")
    public ResponseEntity<?> allHospitalsMatchingMe() {

        return new ResponseEntity<>(patientsService.allHospitalsFilteredByMyNeededOrgan(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @PostMapping("/applyToHospital")
    public ResponseEntity<?> applyToHospital(@RequestParam int hospitalId) {

        return new ResponseEntity<>(patientsService.applyToHospital(hospitalId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @GetMapping("/allHospitalsIApplied")
    public ResponseEntity<?> allHospitalsIApplied() {

        return ResponseEntity.ok(patientsService.allHospitalsIApplied());
    }

    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @PostMapping("/registerInQueue")
    public ResponseEntity<?> registerInQueue(@RequestParam int hospitalId) {

        return ResponseEntity.ok(patientsService.registerInQueue(hospitalId));
    }

    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @GetMapping("/allQueueInThisHospitalIApplied")
    public ResponseEntity<?> allQueueInThisHospitalIApplied(@RequestParam int hospitalId) {

        return ResponseEntity.ok(patientsService.allQueueInThisHospitalIApplied(hospitalId));
    }


    @Authorization(requiredRoles = {"APPROVED_PATIENT"})
    @GetMapping("/allMyOperations")
    public ResponseEntity<?> allMyOperations() {

        return new ResponseEntity<>(patientsService.allMyOperations(), HttpStatus.OK);
    }
}