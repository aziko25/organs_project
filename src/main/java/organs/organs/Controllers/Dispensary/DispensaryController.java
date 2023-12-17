package organs.organs.Controllers.Dispensary;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import organs.organs.Configurations.JWTAuthorization.Authorization;
import organs.organs.Services.Dispensary.DispensaryService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/dispensary")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class DispensaryController {

    private final DispensaryService dispensaryService;

    @Authorization(requiredRoles = {"DISPENSARY"})
    @GetMapping("/myDispensaryInfo")
    public ResponseEntity<?> myDispensaryInfo() {

        return new ResponseEntity<>(dispensaryService.myDispensaryInfo(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @PutMapping("/updateMyDispensaryInfo")
    public ResponseEntity<?> updateMyDispensaryInfo(@RequestParam(required = false) String name) {

        return new ResponseEntity<>(dispensaryService.updateDispensaryInfo(name), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @GetMapping("/allDispensaryDonors")
    public ResponseEntity<?> allDispensaryDonors() {

        return new ResponseEntity<>(dispensaryService.allDispensaryDonors(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @GetMapping("/allDispensaryPatients")
    public ResponseEntity<?> allDispensaryPatients() {

        return new ResponseEntity<>(dispensaryService.allDispensaryPatients(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @PostMapping("/assignDateForDonorsAppointment")
    public ResponseEntity<?> assignDateForDonorsAppointment(@RequestParam int donorId, @RequestParam LocalDateTime time) {

        return new ResponseEntity<>(dispensaryService.assignForAppointmentDonor(donorId, time), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @PostMapping("/assignDateForPatientsAppointment")
    public ResponseEntity<?> assignDateForPatientsAppointment(@RequestParam int patientId, @RequestParam LocalDateTime time) {

        return new ResponseEntity<>(dispensaryService.assignForAppointmentPatient(patientId, time), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @PutMapping("/fillInDonorMedicalCard")
    public ResponseEntity<?> fillInDonorMedicalCard(@RequestParam int donorId, @RequestParam String address,
                                                    @RequestParam String city, @RequestParam String passportNumber,
                                                    @RequestParam String pinfl, @RequestParam Double donationPrice,
                                                    @RequestParam LocalDate birthday, @RequestParam String bloodType,
                                                    @RequestParam String district, @RequestParam String rhFactor,
                                                    @RequestParam int organDonates,
                                                    @RequestParam String comments, @RequestParam Boolean isApproved) {

        return new ResponseEntity<>(dispensaryService.fillInDonorsMedicalCard(donorId,address, city,
                                                                              passportNumber, pinfl, donationPrice,
                                                                              birthday, bloodType, district,
                                                                              rhFactor, organDonates, comments, isApproved), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY", "ADMIN"})
    @PutMapping("/fillInPatientMedicalCard")
    public ResponseEntity<?> fillInPatientMedicalCard(@RequestParam int patientId, @RequestParam String address,
                                                      @RequestParam String city, @RequestParam String passportNumber,
                                                      @RequestParam String pinfl, @RequestParam Integer urgencyRate,
                                                      @RequestParam LocalDate birthday, @RequestParam String bloodType,
                                                      @RequestParam String district, @RequestParam String rhFactor,
                                                      @RequestParam int organReceives,
                                                      @RequestParam String diagnosis, @RequestParam String comments,
                                                      @RequestParam Boolean isApproved) {

        return new ResponseEntity<>(dispensaryService.fillInPatientsMedicalCard(patientId, address, city,
                                                                                passportNumber, pinfl, urgencyRate, birthday,
                                                                                bloodType, district, rhFactor, organReceives,
                                                                                diagnosis, comments, isApproved), HttpStatus.OK);
    }
}