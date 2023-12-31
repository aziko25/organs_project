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
    @GetMapping("/myHospitalInfo")
    public ResponseEntity<?> myHospitalInfo() {

        return new ResponseEntity<>(hospitalsService.myHospitalInfo(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PutMapping("/updateMyHospitalInfo")
    public ResponseEntity<?> updateMyHospitalInfo(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String address,
                                                  @RequestParam(required = false) String description,
                                                  @RequestParam(value = "photo", required = false) MultipartFile multipartFile) {

        return new ResponseEntity<>(hospitalsService.updateMyHospitalInfo(name, address, description, multipartFile), HttpStatus.OK);
    }

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
    @GetMapping("/allMyDoctors")
    public ResponseEntity<?> allMyDoctors() {

        return ResponseEntity.ok(hospitalsService.allMyDoctors());
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/createMyDoctor")
    public ResponseEntity<?> createMyDoctor(@RequestParam String fullName, @RequestParam String email, @RequestParam String specialization) {

        return ResponseEntity.ok(hospitalsService.createDoctor(fullName, email, specialization));
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PutMapping("/updateMyDoctor")
    public ResponseEntity<?> updateMyDoctor(@RequestParam int doctorId, @RequestParam String fullName,
                                            @RequestParam String email, @RequestParam String specialization) {

        return ResponseEntity.ok(hospitalsService.updateDoctor(doctorId, fullName, email, specialization));
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @GetMapping("/allMyOperations")
    public ResponseEntity<?> allMyOperations() {

        return new ResponseEntity<>(hospitalsService.allMyHospitalsOperations(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/appointOperationByTheQueue")
    public ResponseEntity<?> appointOperationByTheQueue(@RequestParam int doctorId,
                                              @RequestParam LocalDateTime time, @RequestParam int donorId) {

        return new ResponseEntity<>(hospitalsService.appointTransportationOperationByTheQueue(doctorId, time, donorId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PostMapping("/appointOperationToAnyPatient")
    public ResponseEntity<?> appointOperationToAnyPatient(@RequestParam int doctorId,
                                              @RequestParam LocalDateTime time, @RequestParam int patientId, @RequestParam int donorId) {

        return new ResponseEntity<>(hospitalsService.appointOperationToAnyPatient(doctorId, time, patientId, donorId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @PutMapping("/updateOperationProperties")
    public ResponseEntity<?> updateOperationProperties(@RequestParam int operationId,
                                                       @RequestParam(required = false) Boolean successful,
                                                       @RequestParam(required = false) LocalDateTime operationTime,
                                                       @RequestParam(required = false) Integer doctorId) {

        return new ResponseEntity<>(hospitalsService.updateOperationProperties(operationId, successful, operationTime, doctorId), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"HOSPITAL"})
    @DeleteMapping("/cancelOperation")
    public ResponseEntity<?> deleteOperation(@RequestParam int operationId) {

        return new ResponseEntity<>(hospitalsService.cancelOperation(operationId), HttpStatus.OK);
    }
}