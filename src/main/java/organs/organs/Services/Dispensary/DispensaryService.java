package organs.organs.Services.Dispensary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import organs.organs.Repositories.ManyToMany.DispensaryDonorsRepository;
import organs.organs.Repositories.ManyToMany.DispensaryPatientsRepository;
import organs.organs.Repositories.UserTypes.DispensaryRepository;

@Service
@RequiredArgsConstructor
public class DispensaryService {

    private final DispensaryRepository dispensaryRepository;
    private final DispensaryDonorsRepository dispensaryDonorsRepository;
    private final DispensaryPatientsRepository dispensaryPatientsRepository;

    public String createDispensary(String name) {

        return null;
    }
}