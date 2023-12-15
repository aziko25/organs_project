package organs.organs.Services.Patients;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatients;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.HospitalsOperations;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.ManyToMany.DispensaryPatientsRepository;
import organs.organs.Repositories.ManyToMany.QueuesHospitalsPatientsRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.HospitalsOperationsRepository;
import organs.organs.Repositories.UserTypes.HospitalsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class PatientsService {

    private final PatientsRepository patientsRepository;
    private final DispensaryPatientsRepository dispensaryPatientsRepository;
    private final DispensaryRepository dispensaryRepository;
    private final HospitalsRepository hospitalsRepository;
    private final QueuesHospitalsPatientsRepository queuesHospitalsPatientsRepository;
    private final HospitalsOperationsRepository hospitalsOperationsRepository;

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

    public List<Hospitals> allHospitalsFilteredByMyNeededOrgan() {

        Patients patient = patientsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Patient Yet!"));

        return hospitalsRepository.findAllBySpecializationOrgans(patient.getOrganReceives());
    }

    @Transactional
    public String applyToHospital(int hospitalId) {

        Patients patient = patientsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Patient Yet!"));
        Hospitals hospital = hospitalsRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("Hospital Not Found!"));

        hospital.getPatients().add(patient);

        hospitalsRepository.save(hospital);

        QueuesHospitalsPatients existingQueuesHospitalsPatients = queuesHospitalsPatientsRepository.findFirstByHospitalId(hospital);

        if (existingQueuesHospitalsPatients == null) {

            throw new IllegalArgumentException("This Hospital Didn't Create The Queue For Its Organs Yet!");
        }

        QueuesHospitalsPatients queuesHospitalsPatients = new QueuesHospitalsPatients();

        queuesHospitalsPatients.setHospitalId(hospital);
        queuesHospitalsPatients.setPatientId(patient);
        queuesHospitalsPatients.setQueueId(existingQueuesHospitalsPatients.getQueueId());

        return "You Successfully Applied To " + hospital.getName();
    }

    public List<HospitalsOperations> allMyOperations() {

        Patients patient = patientsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Patient Yet!"));

        return hospitalsOperationsRepository.findAllByPatientId(patient);
    }
}