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
    @GetMapping("/allDispensaryDonors")
    public ResponseEntity<?> allDispensaryDonors() {

        return new ResponseEntity<>(dispensaryService.allDispensaryDonors(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY"})
    @GetMapping("/allDispensaryPatients")
    public ResponseEntity<?> allDispensaryPatients() {

        return new ResponseEntity<>(dispensaryService.allDispensaryPatients(), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY"})
    @PostMapping("/assignDateForDonorsAppointment")
    public ResponseEntity<?> assignDateForDonorsAppointment(@RequestParam int donorId, @RequestParam LocalDateTime time) {

        return new ResponseEntity<>(dispensaryService.assignForAppointmentDonor(donorId, time), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY"})
    @PostMapping("/assignDateForPatientsAppointment")
    public ResponseEntity<?> assignDateForPatientsAppointment(@RequestParam int patientId, @RequestParam LocalDateTime time) {

        return new ResponseEntity<>(dispensaryService.assignForAppointmentPatient(patientId, time), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY"})
    @PutMapping("/fillInDonorMedicalCard")
    public ResponseEntity<?> fillInDonorMedicalCard(@RequestParam int donorId, @RequestParam String address,
                                                    @RequestParam String city, @RequestParam String passportNumber,
                                                    @RequestParam String pinfl, @RequestParam String donationType,
                                                    @RequestParam LocalDate birthday, @RequestParam String bloodType,
                                                    @RequestParam String district, @RequestParam String rhFactor,
                                                    @RequestParam String diagnosis, @RequestParam String comments,
                                                    @RequestParam Boolean isApproved) {

        return new ResponseEntity<>(dispensaryService.fillInDonorsMedicalCard(donorId,address, city,
                                                                              passportNumber, pinfl, donationType,
                                                                              birthday, bloodType, district,
                                                                              rhFactor, diagnosis, comments, isApproved), HttpStatus.OK);
    }

    @Authorization(requiredRoles = {"DISPENSARY"})
    @PutMapping("/fillInPatientMedicalCard")
    public ResponseEntity<?> fillInPatientMedicalCard(@RequestParam int patientId, @RequestParam String address,
                                                      @RequestParam String city, @RequestParam String passportNumber,
                                                      @RequestParam String pinfl, @RequestParam Integer urgencyRate,
                                                      @RequestParam LocalDate birthday, @RequestParam String bloodType,
                                                      @RequestParam String district, @RequestParam String rhFactor,
                                                      @RequestParam String diagnosis, @RequestParam String comments,
                                                      @RequestParam Boolean isApproved) {

        return new ResponseEntity<>(dispensaryService.fillInPatientsMedicalCard(patientId, address, city,
                                                                                passportNumber, pinfl, urgencyRate, birthday,
                                                                                bloodType, district, rhFactor,
                                                                                diagnosis, comments, isApproved), HttpStatus.OK);
    }
}