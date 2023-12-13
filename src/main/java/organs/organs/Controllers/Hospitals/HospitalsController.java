package organs.organs.Controllers.Hospitals;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Hospitals.HospitalsService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class HospitalsController {

    private final HospitalsService hospitalsService;

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/create")
    public ResponseEntity<?> createHospital(@RequestParam String name, @RequestParam String address,
                                            @RequestParam int specializationOrgan, @RequestParam String description,
                                            @RequestParam("photo") MultipartFile multipartFile) {

        return new ResponseEntity<>(hospitalsService.createHospital(name, address, specializationOrgan, description, multipartFile), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/createQueue")
    public ResponseEntity<?> createQueue(@RequestParam String name) {

        return new ResponseEntity<>(hospitalsService.createQueueForSpecializedOrgan(name), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @GetMapping("/allMyPatients")
    public ResponseEntity<?> allMyPatients() {

        return new ResponseEntity<>(hospitalsService.allMyPatients(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @GetMapping("/allMyPatientsInTheQueue")
    public ResponseEntity<?> allMyPatientsInTheQueue() {

        return new ResponseEntity<>(hospitalsService.allMyPatientsInTheQueue(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @GetMapping("/allMyDonors")
    public ResponseEntity<?> allMyDonors() {

        return new ResponseEntity<>(hospitalsService.allMyDonors(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @GetMapping("/allMyOperations")
    public ResponseEntity<?> allMyOperations() {

        return new ResponseEntity<>(hospitalsService.allMyHospitalsOperations(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/appointOperationByTheQueue")
    public ResponseEntity<?> appointOperationByTheQueue(@RequestParam String doctorName, @RequestParam String doctorRole,
                                              @RequestParam LocalDateTime time, @RequestParam int donorId) {

        return new ResponseEntity<>(hospitalsService.appointTransportationOperationByTheQueue(doctorName, doctorRole, time, donorId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/appointOperationToAnyPatient")
    public ResponseEntity<?> appointOperationToAnyPatient(@RequestParam String doctorName, @RequestParam String doctorRole,
                                              @RequestParam LocalDateTime time, @RequestParam int patientId, @RequestParam int donorId) {

        return new ResponseEntity<>(hospitalsService.appointOperationToAnyPatient(doctorName, doctorRole, time, patientId, donorId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PutMapping("/updateOperationProperties")
    public ResponseEntity<?> updateOperationProperties(@RequestParam int operationId,
                                                       @RequestParam(required = false) Boolean successful,
                                                       @RequestParam(required = false) LocalDateTime operationTime,
                                                       @RequestParam(required = false) String doctorName,
                                                       @RequestParam(required = false) String doctorRole) {

        return new ResponseEntity<>(hospitalsService.updateOperationProperties(operationId, successful, operationTime, doctorName, doctorRole), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @DeleteMapping("/cancelOperation")
    public ResponseEntity<?> deleteOperation(@RequestParam int operationId) {

        return new ResponseEntity<>(hospitalsService.cancelOperation(operationId), HttpStatus.OK);
    }
}