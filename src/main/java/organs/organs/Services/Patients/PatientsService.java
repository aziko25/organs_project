package organs.organs.Services.Patients;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatients;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.ManyToMany.DispensaryPatientsRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class PatientsService {

    private final UsersRepository usersRepository;
    private final PatientsRepository patientsRepository;
    private final DispensaryPatientsRepository dispensaryPatientsRepository;
    private final DispensaryRepository dispensaryRepository;

    public String becomePatient() {

        try {

            Patients patient = new Patients();

            patient.setUserId(USER);

            patientsRepository.save(patient);

            return "You Successfully Became Patient!";
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Are Already A Patient!");
        }
    }

    public String applyToDispensary(int dispensaryId, String phoneNumber) {

        Patients patient = patientsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Patient!"));

        Pattern pattern = Pattern.compile("^(\\+)?998[35789]\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {

            patient.setPhoneNumber(phoneNumber);
        }
        else {

            throw new IllegalArgumentException("Phone Number Format Is Incorrect");
        }

        Dispensary dispensary = dispensaryRepository.findById(dispensaryId).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryPatients> dispensaryPatientsList = dispensaryPatientsRepository.findAllByPatientIdAndDispensaryId(patient, dispensary);
        DispensaryPatients lastDispensaryPatient = dispensaryPatientsList.get(dispensaryPatientsList.size() - 1);

        LocalDateTime now = LocalDateTime.now();

        if (!dispensaryPatientsList.isEmpty() && (lastDispensaryPatient.getDate() == null ||
                now.isBefore(lastDispensaryPatient.getDate()))) {

            throw new IllegalArgumentException("You Already Have An Appointment In This Dispensary!");
        }

        DispensaryPatients dispensaryPatients = new DispensaryPatients();

        dispensaryPatients.setPatientId(patient);
        dispensaryPatients.setDispensaryId(dispensary);

        dispensaryPatientsRepository.save(dispensaryPatients);
        patientsRepository.save(patient);

        return "You Successfully Sent A Request To Dispensary!";
    }

    public List<DispensaryPatients> allMyDispensaryVisits() {

        Patients patient = patientsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor"));

        return dispensaryPatientsRepository.findAllByPatientId(patient);
    }
}
