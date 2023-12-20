package organs.organs.Controllers.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Admins.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
public class AdminsController {

    private final AdminsDispensaryService adminsDispensaryService;
    private final AdminsHospitalsService adminsHospitalsService;
    private final AdminsDonorsService adminsDonorsService;
    private final AdminsPatientsService adminsPatientsService;
    private final AdminsOrgansService adminsOrgansService;
    private final AdminsRegionsService adminsRegionsService;

    // REGIONS

    @GetMapping("/regions/allRegions")
    public ResponseEntity<?> allRegions() {

        return ResponseEntity.ok(adminsRegionsService.allRegions());
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/regions/createRegion")
    public ResponseEntity<?> createRegion(@RequestParam String name) {

        return ResponseEntity.ok(adminsRegionsService.createRegion(name));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/regions/updateRegion")
    public ResponseEntity<?> updateRegion(@RequestParam int regionId, @RequestParam String name) {

        return ResponseEntity.ok(adminsRegionsService.updateRegion(regionId, name));
    }

    // ORGANS

    //@Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/organs/allOrgans")
    public ResponseEntity<?> allOrgans() {

        return ResponseEntity.ok(adminsOrgansService.allOrgans());
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/organs/createOrgan")
    public ResponseEntity<?> createOrgan(@RequestParam String name) {

        return ResponseEntity.ok(adminsOrgansService.createOrgan(name));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/organs/updateOrgan")
    public ResponseEntity<?> updateOrgan(@RequestParam int organId, @RequestParam(required = false) String name) {

        return ResponseEntity.ok(adminsOrgansService.updateOrgan(organId, name));
    }

    // DISPENSARY

    @Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/dispensary/allDispensaries")
    public ResponseEntity<?> allDispensaries() {

        return new ResponseEntity<>(adminsDispensaryService.allDispensaries(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/dispensary/createDispensary")
    public ResponseEntity<?> createDispensary(@RequestParam String name) {

        return new ResponseEntity<>(adminsDispensaryService.createDispensary(name), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/dispensary/updateDispensary")
    public ResponseEntity<?> updateDispensary(@RequestParam int dispensaryId, @RequestParam(required = false) String name) {

        return new ResponseEntity<>(adminsDispensaryService.updateDispensary(dispensaryId, name), HttpStatus.OK);
    }

    // HOSPITALS

    @Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/hospitals/allHospitals")
    public ResponseEntity<?> allHospitals() {

        return new ResponseEntity<>(adminsHospitalsService.allHospitals(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/hospitals/createHospital")
    public ResponseEntity<?> createHospital(@RequestParam String name, @RequestParam int specializationOrganId,
                                            @RequestParam String address) {

        return new ResponseEntity<>(adminsHospitalsService.createHospital(name, specializationOrganId, address), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/hospitals/updateHospital")
    public ResponseEntity<?> updateHospital(@RequestParam int hospitalId, @RequestParam(required = false) String name, @RequestParam(required = false) String address) {

        return new ResponseEntity<>(adminsHospitalsService.updateHospital(hospitalId, name, address), HttpStatus.OK);
    }

    // DONORS

    @Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/donors/allDonors")
    public ResponseEntity<?> allDonors() {

        return ResponseEntity.ok(adminsDonorsService.allDonors());
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/donors/createDonor")
    public ResponseEntity<?> createDonor(@RequestParam String fullName, @RequestParam String email, @RequestParam String password) {

        return ResponseEntity.ok(adminsDonorsService.createDonor(fullName, email, password));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/donors/updateDonor")
    public ResponseEntity<?> updateDonor(@RequestParam int donorId, @RequestParam(required = false) String address,
                                         @RequestParam(required = false) String city, @RequestParam(required = false) Double donationPrice,
                                         @RequestParam(required = false) LocalDate birthday, @RequestParam(required = false) String bloodType,
                                         @RequestParam(required = false) String district, @RequestParam(required = false) String rhFactor,
                                         @RequestParam(required = false) String comments) {

        return ResponseEntity.ok(adminsDonorsService.updateDonorsInfo(donorId, address, city, donationPrice, birthday, bloodType, district, rhFactor, comments));
    }

    // PATIENTS

    @Authorization(requiredRoles = {"ADMIN"})
    @GetMapping("/patients/allPatients")
    public ResponseEntity<?> allPatients() {

        return ResponseEntity.ok(adminsPatientsService.allPatients());
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PostMapping("/patients/createPatient")
    public ResponseEntity<?> createPatient(@RequestParam String fullName, @RequestParam String email, @RequestParam String password) {

        return ResponseEntity.ok(adminsPatientsService.createPatient(fullName, email, password));
    }

    @Authorization(requiredRoles = {"ADMIN"})
    @PutMapping("/patients/updatePatient")
    public ResponseEntity<?> updatePatient(@RequestParam int patientId, @RequestParam(required = false) String address,
                                           @RequestParam(required = false) String city,
                                           @RequestParam(required = false) LocalDate birthday,
                                           @RequestParam(required = false) String bloodType,
                                           @RequestParam(required = false) String district,
                                           @RequestParam(required = false) String rhFactor,
                                           @RequestParam(required = false) String comments) {

        return ResponseEntity.ok(adminsPatientsService.updatePatientsInfo(patientId, address, city, birthday, bloodType, district, rhFactor, comments));
    }
}