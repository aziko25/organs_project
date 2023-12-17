package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Services.Dispensary.DispensaryService;

import java.util.List;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class AdminsDispensaryService {

    private final DispensaryRepository dispensaryRepository;

    public List<Dispensary> allDispensaries() {

        return dispensaryRepository.findAll(Sort.by("id"));
    }

    public String createDispensary(String name) {

        Dispensary dispensary = dispensaryRepository.findByName(name);

        if (dispensary != null) {

            throw new IllegalArgumentException("Dispensary With This Name Already Exists!");
        }

        dispensary = new Dispensary();

        dispensary.setName(name);
        dispensary.setCreatorId(USER);

        dispensaryRepository.save(dispensary);

        return "You Successfully Created Dispensary!";
    }

    public String updateDispensary(int dispensaryId, String name) {

        Dispensary dispensary = dispensaryRepository.findById(dispensaryId).orElseThrow(() -> new IllegalArgumentException("Dispensary Not Found"));

        if (name != null) {

            dispensary.setName(name);
        }

        dispensaryRepository.save(dispensary);

        return "You Successfully Updated Dispensary!";
    }
}