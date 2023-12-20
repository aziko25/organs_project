package organs.organs.Services.Users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import organs.organs.Models.Regions;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.RegionsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import static organs.organs.Services.Authentication.LoginService.EMAIL;
import static organs.organs.Services.Hospitals.HospitalsService.handleFileUpload;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final RegionsRepository regionsRepository;

    public Users myInfo() {

        return usersRepository.findByEmail(EMAIL).orElseThrow();
    }

    public String userUploadPhoto(MultipartFile photo) {

        Users user = usersRepository.findByEmail(EMAIL).orElseThrow();

        String fileName = handleFileUpload(user.getFullName(), photo);
        user.setImageLink(fileName);

        usersRepository.save(user);

        return "You Successfully Uploaded Your Profile Photo!";
    }

    public String userChangeRegion(Integer regionId) {

        Users user = usersRepository.findByEmail(EMAIL).orElseThrow(() -> new IllegalArgumentException("You Are Not Logged In!"));

        if (regionId != null) {

            Regions region = regionsRepository.findById(regionId).orElseThrow(() -> new IllegalArgumentException("Region Not Found!"));

            user.setRegionId(region);
        }

        usersRepository.save(user);

        return "You Successfully Changed Your Region!";
    }
}