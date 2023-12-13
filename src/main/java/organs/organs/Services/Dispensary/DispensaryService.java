package organs.organs.Services.Dispensary;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatients;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.ManyToMany.DispensaryDonorsRepository;
import organs.organs.Repositories.ManyToMany.DispensaryPatientsRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DispensaryService {

    private final DispensaryRepository dispensaryRepository;
    private final DispensaryDonorsRepository dispensaryDonorsRepository;
    private final DispensaryPatientsRepository dispensaryPatientsRepository;
    private final DonorsRepository donorsRepository;
    private final PatientsRepository patientsRepository;

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

        lastDispensaryDonor.setDate(time);

        dispensaryDonorsRepository.save(lastDispensaryDonor);

        return "You Successfully Assigned Appointment For " + donor.getUserId().getFullName() + " At " + time;
    }

    public String assignForAppointmentPatient(int patientId, LocalDateTime time) {

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found"));
        Dispensary dispensary = dispensaryRepository.findById(1).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryPatients> dispensaryPatientsList = dispensaryPatientsRepository.findAllByPatientIdAndDispensaryId(patient, dispensary);
        DispensaryPatients lastDispensaryPatient = dispensaryPatientsList.get(dispensaryPatientsList.size() - 1);

        lastDispensaryPatient.setDate(time);

        dispensaryPatientsRepository.save(lastDispensaryPatient);

        return "You Successfully Assigned Appointment For " + patient.getUserId().getFullName() + " At " + time;
    }

    public String fillInDonorsMedicalCard(int donorId, String address, String city, String passportNumber,
                                          String pinfl, String donationType, LocalDate birthday, String bloodType,
                                          String district, String rhFactor, String diagnosis, String comments,
                                          Boolean isApproved) {

        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Not Found!"));

        donor.setAddress(address);
        donor.setCity(city);
        donor.setPassportNumber(passportNumber);
        donor.setPinfl(pinfl);
        donor.setDonationType(donationType);
        donor.setBirthday(birthday);
        donor.setBloodType(bloodType);
        donor.setDistrict(district);
        donor.setRhFactor(rhFactor);
        donor.setDiagnosis(diagnosis);
        donor.setComments(comments);
        donor.setIsApproved(isApproved);

        donorsRepository.save(donor);

        return "You Successfully Filled Out Donors Information";
    }

    public String fillInPatientsMedicalCard(int patientId, String address, String city, String passportNumber,
                                          String pinfl, Integer urgencyRate, LocalDate birthday, String bloodType,
                                          String district, String rhFactor, String diagnosis, String comments,
                                          Boolean isApproved) {

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found!"));

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

        patientsRepository.save(patient);

        return "You Successfully Filled Out Patients Information";
    }
}