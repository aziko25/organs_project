package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminsOrgansService {

    private final OrgansRepository organsRepository;

    public List<Organs> allOrgans() {

        return organsRepository.findAll(Sort.by("id"));
    }

    public String createOrgan(String name) {

        Organs organ = organsRepository.findByName(name);

        if (organ != null) {

            throw new IllegalArgumentException("Organ Already Exists!");
        }

        organ = new Organs();

        organ.setName(name);

        organsRepository.save(organ);

        return "You Successfully Created " + organ.getName();
    }

    public String updateOrgan(int organId, String name) {

        Organs organ = organsRepository.findByName(name);

        if (organ != null) {

            throw new IllegalArgumentException("Organ " + name + " Already Exists!");
        }

        organ = organsRepository.findById(organId).orElseThrow(() -> new IllegalArgumentException("Organ Not Found!"));

        organ.setName(name);

        organsRepository.save(organ);

        return "You Successfully Updated " + name;
    }
}