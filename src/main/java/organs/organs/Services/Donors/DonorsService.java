package organs.organs.Services.Donors;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.ManyToMany.DispensaryDonorsRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class DonorsService {

    private final UsersRepository usersRepository;
    private final DonorsRepository donorsRepository;
    private final DispensaryRepository dispensaryRepository;
    private final DispensaryDonorsRepository dispensaryDonorsRepository;

    public String becomeDonor() {

        try {

            Donors donor = new Donors();

            donor.setUserId(USER);

            donorsRepository.save(donor);

            return "You Successfully Became Donor!";
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Are Already A Donor!");
        }
    }

    public String applyToDispensary(int dispensaryId, String phoneNumber) {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor!"));

        Pattern pattern = Pattern.compile("^(\\+)?998[35789]\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        if (matcher.matches()) {

            donor.setPhoneNumber(phoneNumber);
        }
        else {

            throw new IllegalArgumentException("Phone Number Format Is Incorrect");
        }

        Dispensary dispensary = dispensaryRepository.findById(dispensaryId).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        List<DispensaryDonors> dispensaryDonorsList = dispensaryDonorsRepository.findAllByDonorIdAndDispensaryId(donor, dispensary);
        DispensaryDonors lastDispensaryDonor = dispensaryDonorsList.get(dispensaryDonorsList.size() - 1);

        LocalDateTime now = LocalDateTime.now();

        if (!dispensaryDonorsList.isEmpty() && (lastDispensaryDonor.getDate() == null ||
                now.isBefore(lastDispensaryDonor.getDate()))) {

            throw new IllegalArgumentException("You Already Have An Appointment In This Dispensary!");
        }

        DispensaryDonors dispensaryDonors = new DispensaryDonors();

        dispensaryDonors.setDonorId(donor);
        dispensaryDonors.setDispensaryId(dispensary);

        dispensaryDonorsRepository.save(dispensaryDonors);
        donorsRepository.save(donor);

        return "You Successfully Sent A Request To Dispensary!";
    }

    public List<DispensaryDonors> allMyDispensaryVisits() {

        Donors donor = donorsRepository.findByUserId(USER).orElseThrow(() -> new IllegalArgumentException("You Are Not A Donor"));

        return dispensaryDonorsRepository.findAllByDonorId(donor);
    }
}