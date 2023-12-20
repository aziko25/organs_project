package organs.organs.Services.Admins;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import organs.organs.Models.Regions;
import organs.organs.Repositories.RegionsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminsRegionsService {

    private final RegionsRepository regionsRepository;

    public List<Regions> allRegions() {

        return regionsRepository.findAll(Sort.by("name"));
    }

    public String createRegion(String name) {

        try {

            Regions region = new Regions();

            region.setName(name);

            regionsRepository.save(region);

            return "You Successfully Created " + name;
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("Region " + name + " Already Exists!");
        }
    }

    public String updateRegion(int regionId, String name) {

        try {

            Regions region = regionsRepository.findById(regionId).orElseThrow(() -> new IllegalArgumentException("Region Not Found!"));

            if (name != null) {

                region.setName(name);
            }

            regionsRepository.save(region);

            return "You Successfully Updated Region To " + name;
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("Region " + name + " Already Exists!");
        }
    }
}