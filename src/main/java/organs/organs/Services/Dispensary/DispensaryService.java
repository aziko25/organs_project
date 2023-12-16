package organs.organs.Services.Dispensary;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatients;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.ManyToMany.DispensaryDonorsRepository;
import organs.organs.Repositories.ManyToMany.DispensaryPatientsRepository;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static organs.organs.Services.Authentication.LoginService.EMAIL;
import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class DispensaryService {

    private final UsersRepository usersRepository;
    private final DispensaryRepository dispensaryRepository;
    private final DispensaryDonorsRepository dispensaryDonorsRepository;
    private final DispensaryPatientsRepository dispensaryPatientsRepository;
    private final DonorsRepository donorsRepository;
    private final PatientsRepository patientsRepository;
    private final OrgansRepository organsRepository;

    public Dispensary myDispensaryInfo() {

        return dispensaryRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Dispensary"));
    }

    public List<DispensaryDonors> allDispensaryDonors() {

        return dispensaryDonorsRepository.findAll(Sort.by("date"));
    }

    public List<DispensaryPatients> allDispensaryPatients() {

        return dispensaryPatientsRepository.findAll(Sort.by("date"));
    }

    public String assignForAppointmentDonor(int donorId, LocalDateTime time) {

        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Not Found"));
        Dispensary dispensary = dispensaryRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryDonors> dispensaryDonorsList = dispensaryDonorsRepository.findAllByDonorIdAndDispensaryId(donor, dispensary);
        DispensaryDonors lastDispensaryDonor = dispensaryDonorsList.get(dispensaryDonorsList.size() - 1);

        if (lastDispensaryDonor.getDate() != null) {

            throw new IllegalArgumentException("You Already Assigned An Appointment To This Donor!");
        }

        lastDispensaryDonor.setDate(time);

        dispensaryDonorsRepository.save(lastDispensaryDonor);

        return "You Successfully Assigned Appointment For " + donor.getUserId().getFullName() + " At " + time;
    }

    public String assignForAppointmentPatient(int patientId, LocalDateTime time) {

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found"));
        Dispensary dispensary = dispensaryRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryPatients> dispensaryPatientsList = dispensaryPatientsRepository.findAllByPatientIdAndDispensaryId(patient, dispensary);
        DispensaryPatients lastDispensaryPatient = dispensaryPatientsList.get(dispensaryPatientsList.size() - 1);

        if (lastDispensaryPatient.getDate() != null) {

            throw new IllegalArgumentException("You Already Assigned An Appointment To This Patient!");
        }

        lastDispensaryPatient.setDate(time);

        dispensaryPatientsRepository.save(lastDispensaryPatient);

        return "You Successfully Assigned Appointment For " + patient.getUserId().getFullName() + " At " + time;
    }

    public String fillInDonorsMedicalCard(int donorId, String address, String city, String passportNumber,
                                          String pinfl, Double donationPrice, LocalDate birthday, String bloodType,
                                          String district, String rhFactor, int organId, String comments,
                                          Boolean isApproved) {

        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Not Found!"));
        DispensaryDonors dispensaryDonors = dispensaryDonorsRepository.findByDonorIdAndIsActive(donor, true);

        if (dispensaryDonors == null) {

            throw new IllegalArgumentException("This Donor Didn't Apply To The Dispensary!");
        }

        if (dispensaryDonors.getDate() == null) {

            throw new IllegalArgumentException("First, Set Appointment Time To This Donor!");
        }

        try {
            Organs organ = organsRepository.findById(organId).orElseThrow(() -> new IllegalArgumentException("Organ Not Found!"));

            donor.setAddress(address);
            donor.setCity(city);
            donor.setPassportNumber(passportNumber);
            donor.setPinfl(pinfl);
            donor.setDonationPrice(donationPrice);
            donor.setBirthday(birthday);
            donor.setBloodType(bloodType);
            donor.setDistrict(district);
            donor.setRhFactor(rhFactor);
            donor.setComments(comments);
            donor.setIsApproved(isApproved);
            donor.setOrganDonates(organ);

            if (isApproved) {

                Users user = usersRepository.findById(donor.getUserId().getId()).orElseThrow();

                user.setRole("APPROVED_DONOR");

                usersRepository.save(user);
            }

            dispensaryDonors.setIsActive(false);

            dispensaryDonorsRepository.save(dispensaryDonors);
            donorsRepository.save(donor);
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("Passport Number Or Pinfl Already Exists!");
        }

        return "You Successfully Filled Out Donors Information";
    }

    public String fillInPatientsMedicalCard(int patientId, String address, String city, String passportNumber,
                                          String pinfl, Integer urgencyRate, LocalDate birthday, String bloodType,
                                          String district, String rhFactor, int organId, String diagnosis, String comments,
                                          Boolean isApproved) {

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found!"));
        DispensaryPatients dispensaryPatients = dispensaryPatientsRepository.findByPatientIdAndIsActive(patient, true);
        Organs organ = organsRepository.findById(organId).orElseThrow(() -> new IllegalArgumentException("Organ Not Found!"));

        if (dispensaryPatients == null) {

            throw new IllegalArgumentException("This Patient Didn't Apply To The Dispensary!");
        }

        if (dispensaryPatients.getDate() == null) {

            throw new IllegalArgumentException("First, Set The Date Of Appointment To This Operation!");
        }

        patient.setAddress(address);
        patient.setCity(city);
        patient.setPassportNumber(passportNumber);
        patient.setPinfl(pinfl);
        patient.setUrgencyRate(urgencyRate);
        patient.setBirthday(birthday);
        patient.setBloodType(bloodType);
        patient.setDistrict(district);
        patient.setRhFactor(rhFactor);
        patient.setDiagnosis(diagnosis);
        patient.setComments(comments);
        patient.setIsApproved(isApproved);
        patient.setOrganReceives(organ);

        if (isApproved) {

            Users user = usersRepository.findById(patient.getUserId().getId()).orElseThrow();

            user.setRole("APPROVED_PATIENT");

            usersRepository.save(user);
        }

        dispensaryPatients.setIsActive(false);

        dispensaryPatientsRepository.save(dispensaryPatients);
        patientsRepository.save(patient);

        return "You Successfully Filled Out Patients Information";
    }
}