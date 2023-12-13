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
    @PostMapping("/appointOperationByTheQueue")
    public ResponseEntity<?> appointOperation(@RequestParam String doctorName, @RequestParam String doctorRole,
                                              @RequestParam LocalDateTime time) {

        return new ResponseEntity<>(hospitalsService.appointTransportationOperationByTheQueue(doctorName, doctorRole, time), HttpStatus.OK);
    }
}