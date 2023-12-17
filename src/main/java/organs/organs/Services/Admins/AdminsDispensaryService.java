package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Users;
import organs.organs.Repositories.UserTypes.DispensaryRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminsDispensaryService {

    private final DispensaryRepository dispensaryRepository;
    private final UsersRepository usersRepository;

    public List<Dispensary> allDispensaries() {

        return dispensaryRepository.findAll(Sort.by("id"));
    }

    public String createDispensary(String name) {

        Dispensary dispensary = dispensaryRepository.findByName(name);

        if (dispensary != null) {

            throw new IllegalArgumentException("Dispensary With This Name Already Exists!");
        }

        Users user = new Users();

        user.setFullName("Dispensary's Owner");
        user.setPassword(name);
        user.setRole("DISPENSARY");
        user.setEmail(name + "@mail.ru");

        usersRepository.save(user);

        dispensary = new Dispensary();

        dispensary.setName(name);
        dispensary.setCreatorId(user);

        dispensaryRepository.save(dispensary);

        return "You Successfully Created Dispensary! \nDispensaries Details:\nEmail: " + user.getEmail() + "\nPassword: " + user.getPassword();
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