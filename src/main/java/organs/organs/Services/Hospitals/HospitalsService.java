package organs.organs.Services.Hospitals;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import organs.organs.Configurations.Images.FileUploadUtil;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgans;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.OrgansAndQueues.Queues;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.ManyToMany.HospitalsDonorsOrgansRepository;
import organs.organs.Repositories.ManyToMany.QueuesHospitalsPatientsRepository;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.OrgansAndQueues.QueuesRepository;
import organs.organs.Repositories.UserTypes.DonorsRepository;
import organs.organs.Repositories.UserTypes.HospitalsRepository;
import organs.organs.Repositories.UserTypes.PatientsRepository;
import organs.organs.Repositories.UserTypes.UsersRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class HospitalsService {

    private final UsersRepository usersRepository;
    private final DonorsRepository donorsRepository;
    private final PatientsRepository patientsRepository;
    private final HospitalsRepository hospitalsRepository;
    private final OrgansRepository organsRepository;
    private final HospitalsDonorsOrgansRepository hospitalsDonorsOrgansRepository;
    private final QueuesRepository queuesRepository;
    private final QueuesHospitalsPatientsRepository queuesHospitalsPatientsRepository;

    public String createHospital(String name, String address, int specializedOrgan, String description, MultipartFile photo) {

        Organs organ = organsRepository.findById(specializedOrgan).orElseThrow(() -> new IllegalArgumentException("Organ Not Found"));

        Hospitals hospital = hospitalsRepository.findByName(name).orElse(new Hospitals());

        hospital.setName(name);
        hospital.setAddress(address);
        hospital.setSpecializationOrgans(organ);
        hospital.setDescription(description);

        String fileName = handleFileUploadForHospitals(name, photo);
        hospital.setImageLink(fileName);

        hospitalsRepository.save(hospital);

        return "You Successfully Created " + name + "!";
    }

    public String createQueueForSpecializedOrgan(String name) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));

        Organs organ = organsRepository.findById(hospital.getSpecializationOrgans().getId()).orElseThrow(() -> new IllegalArgumentException("Organ Not Found"));

        Queues queue = new Queues();

        queue.setName(name);
        queue.setOrganId(organ);

        queuesRepository.save(queue);

        try {

            QueuesHospitalsPatients queuesHospitalsPatients = new QueuesHospitalsPatients();

            queuesHospitalsPatients.setQueueId(queue);
            queuesHospitalsPatients.setHospitalId(hospital);

            queuesHospitalsPatientsRepository.save(queuesHospitalsPatients);
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Already Have A Queue For Your Organ!");
        }

        return "You Successfully Created A Queue";
    }

    public Set<Patients> allMyPatients() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return hospital.getPatients();
    }

    public List<QueuesHospitalsPatients> allPatientsInTheQueue() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return queuesHospitalsPatientsRepository.findAllByHospitalId(hospital);
    }

    public String appointTransportationOperation(String doctorName, String role, LocalDateTime time) {

        return null;
    }

    public List<HospitalsDonorsOrgans> allMyDonors() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return hospitalsDonorsOrgansRepository.findAllByHospitalId(hospital);
    }

    private String handleFileUploadForHospitals(String hospitalName, MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {

            throw new IllegalArgumentException("Upload A File!");
        }

        String originalFileName = multipartFile.getOriginalFilename();

        assert originalFileName != null;
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        if (!fileExtension.matches(".png|.heic|.jpg|.jpeg")) {

            throw new IllegalArgumentException("Invalid file type! Please upload a .png, .heic, .jpg, or .jpeg file.");
        }

        String fileName = hospitalName + "_avatar" + fileExtension;
        String uploadDir = "/var/www/images";

        try {

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        catch (IOException e) {

            e.printStackTrace();
        }

        return fileName;
    }
}