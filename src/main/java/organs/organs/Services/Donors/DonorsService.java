package organs.organs.Services.Donors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import organs.organs.Configurations.Mail.EmailService;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgans;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.HospitalsOperations;
import organs.organs.Repositories.ManyToMany.DispensaryDonorsRepository;
import organs.organs.Repositories.ManyToMany.HospitalsDonorsOrgansRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.HospitalsOperationsRepository;
import organs.organs.Repositories.UserTypes.HospitalsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class DonorsService {

    private final DonorsRepository donorsRepository;
    private final DispensaryRepository dispensaryRepository;
    private final DispensaryDonorsRepository dispensaryDonorsRepository;
    private final HospitalsRepository hospitalsRepository;
    private final HospitalsDonorsOrgansRepository hospitalsDonorsOrgansRepository;
    private final HospitalsOperationsRepository hospitalsOperationsRepository;
    private final EmailService emailService;

    public Donors myDonorInfo() {

        return donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor!"));
    }

    public String applyToDispensary(int dispensaryId, String phoneNumber) {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor!"));

        Pattern pattern = Pattern.compile("^(\\+)?998[123456789]\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {

            donor.setPhoneNumber(phoneNumber);
        }
        else {

            throw new IllegalArgumentException("Phone Number Format Is Incorrect");
        }

        Dispensary dispensary = dispensaryRepository.findById(dispensaryId).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryDonors> dispensaryDonorsList = dispensaryDonorsRepository.findAllByDonorIdAndDispensaryId(donor, dispensary);

        if (dispensaryDonorsList != null && !dispensaryDonorsList.isEmpty()) {

            DispensaryDonors lastDispensaryDonor = dispensaryDonorsList.get(dispensaryDonorsList.size() - 1);

            LocalDateTime now = LocalDateTime.now();

            if (!dispensaryDonorsList.isEmpty() && (lastDispensaryDonor.getDate() == null ||
                    now.isBefore(lastDispensaryDonor.getDate()))) {

                throw new IllegalArgumentException("You Already Have An Appointment In This Dispensary!");
            }
        }

        DispensaryDonors dispensaryDonors = new DispensaryDonors();

        dispensaryDonors.setDonorId(donor);
        dispensaryDonors.setDispensaryId(dispensary);
        dispensaryDonors.setIsActive(true);

        dispensaryDonorsRepository.save(dispensaryDonors);
        donorsRepository.save(donor);

        return "You Successfully Sent A Request To Dispensary!";
    }

    public List<DispensaryDonors> allMyDispensaryVisits() {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor"));

        return dispensaryDonorsRepository.findAllByDonorId(donor);
    }

    public List<Hospitals> allHospitalsFilteredByMyDonatingOrgan() {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor Yet!"));

        return hospitalsRepository.findAllBySpecializationOrgans(donor.getOrganDonates());
    }

    @Transactional
    public String applyToHospital(int hospitalId) {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor Yet!"));
        Hospitals hospital = hospitalsRepository.findByIdAndSpecializationOrgans(hospitalId, donor.getOrganDonates()).orElseThrow(() -> new IllegalArgumentException("This Hospital Does Not Support Your Organ!"));

        try {

            HospitalsDonorsOrgans hospitalsDonorsOrgans = new HospitalsDonorsOrgans();

            hospitalsDonorsOrgans.setHospitalId(hospital);
            hospitalsDonorsOrgans.setDonorId(donor);
            hospitalsDonorsOrgans.setOrganId(donor.getOrganDonates());
            hospitalsDonorsOrgans.setPrice(donor.getDonationPrice());

            hospitalsDonorsOrgansRepository.save(hospitalsDonorsOrgans);
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Are Already A Donor Here!");
        }

        return "You Successfully Applied To " + hospital.getName() + " As Donor!";
    }

    public String acceptOrRejectOperation(int operationId, boolean decision) {

        HospitalsOperations hospitalsOperations = hospitalsOperationsRepository.findById(operationId).orElseThrow(() -> new IllegalArgumentException("Operation Not Found"));
        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor!"));

        if (donor != hospitalsOperations.getDonorId()) {

            throw new IllegalArgumentException("This Operation Is Not Related To You!");
        }

        hospitalsOperations.setIsDonorAccepted(decision);

        hospitalsOperationsRepository.save(hospitalsOperations);

        emailService.sendCodeToEmail(hospitalsOperations.getPatientId().getUserId().getEmail(), "Rejected Operation", "Donor Has Rejected To Donate His Organ To Your Operation! You Need To Stand In The Queue Again!");
        emailService.sendCodeToEmail(hospitalsOperations.getHospitalId().getCreatorId().getEmail(), "Rejected Operation", "Donor Has Rejected To Donate His Organ To Your Operation!");

        return "You Successfully Sent Your Decision!";
    }

    public List<HospitalsOperations> allMyOperations() {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Patient Yet!"));

        return hospitalsOperationsRepository.findAllByDonorId(donor);
    }
}