package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminsDonorsService {

    private final DonorsRepository donorsRepository;
    private final UsersRepository usersRepository;

    public List<Donors> allDonors() {

        return donorsRepository.findAll(Sort.by("id"));
    }

    public String createDonor(String fullName, String email, String password) {

        Optional<Users> userExists = usersRepository.findByEmail(email);

        if (userExists.isPresent()) {

            throw new IllegalArgumentException("This Email Already Exists!");
        }

        Users user = new Users();

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        usersRepository.save(user);

        Donors donor = new Donors();

        donor.setUserId(user);

        donorsRepository.save(donor);

        return "You Successfully Created " + fullName + " Donor!";
    }

    public String updateDonorsInfo(int donorId, String address, String city,
                                        Double donationPrice, LocalDate birthday, String bloodType,
                                        String district, String rhFactor, String comments) {

        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Not Found!"));

        if (address != null) {
            donor.setAddress(address);
        }

        if (city != null) {
            donor.setCity(city);
        }

        if (donationPrice != null) {
            donor.setDonationPrice(donationPrice);
        }

        if (birthday != null) {
            donor.setBirthday(birthday);
        }

        if (bloodType != null) {
            donor.setBloodType(bloodType);
        }

        if (district != null) {
            donor.setDistrict(district);
        }

        if (rhFactor != null) {
            donor.setRhFactor(rhFactor);
        }

        if (comments != null) {
            donor.setComments(comments);
        }

        donorsRepository.save(donor);

        return "You Successfully Updated Donors Medical Card!";
    }
}