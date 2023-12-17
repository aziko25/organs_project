package organs.organs.Services.Hospitals;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import organs.organs.Configurations.Images.FileUploadUtil;
import organs.organs.Configurations.Mail.EmailService;
import organs.organs.Models.ManyToMany.HospitalsDonorsOrgans.HospitalsDonorsOrgans;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.OrgansAndQueues.Queues;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Hospitals;
import organs.organs.Models.UserTypes.HospitalsOperations;
import organs.organs.Models.UserTypes.Patients;
import organs.organs.Repositories.ManyToMany.HospitalsDonorsOrgansRepository;
import organs.organs.Repositories.ManyToMany.QueuesHospitalsPatientsRepository;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.OrgansAndQueues.QueuesRepository;
import organs.organs.Repositories.UserTypes.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static organs.organs.Services.Authentication.LoginService.USER;

@Service
@RequiredArgsConstructor
public class HospitalsService {

    private final DonorsRepository donorsRepository;
    private final PatientsRepository patientsRepository;
    private final HospitalsRepository hospitalsRepository;
    private final OrgansRepository organsRepository;
    private final HospitalsDonorsOrgansRepository hospitalsDonorsOrgansRepository;
    private final QueuesRepository queuesRepository;
    private final QueuesHospitalsPatientsRepository queuesHospitalsPatientsRepository;
    private final HospitalsOperationsRepository hospitalsOperationsRepository;
    private final EmailService emailService;

    public Hospitals myHospitalInfo() {

        return hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));
    }

    public String updateMyHospitalInfo(String name, String address, String description, MultipartFile photo) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));

        if (name != null) {

            hospital.setName(name);
        }

        if (address != null) {

            hospital.setAddress(address);
        }

        if (description != null) {

            hospital.setDescription(description);
        }

        if (photo != null && !photo.isEmpty()) {

            String fileName = handleFileUploadForHospitals(name, photo);
            hospital.setImageLink(fileName);
        }

        hospitalsRepository.save(hospital);

        return "You Successfully Updated Hospitals Information!";
    }

    public String createHospital(String name, String address, int specializedOrgan, String description, MultipartFile photo) {

        Organs organ = organsRepository.findById(specializedOrgan).orElseThrow(() -> new IllegalArgumentException("Organ Not Found"));

        Optional<Hospitals> existingHospital = hospitalsRepository.findByName(name);

        if (existingHospital.isPresent()) {

            throw new IllegalArgumentException("Hospital With This Name Already Exists!");
        }

        try {

            Hospitals hospital = new Hospitals();

            hospital.setName(name);
            hospital.setAddress(address);
            hospital.setSpecializationOrgans(organ);
            hospital.setDescription(description);
            hospital.setCreatorId(USER);

            String fileName = handleFileUploadForHospitals(name, photo);
            hospital.setImageLink(fileName);

            hospitalsRepository.save(hospital);
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Already Have A Hospital!");
        }

        return "You Successfully Created " + name + "!";
    }

    public String createQueueForSpecializedOrgan(String name) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));

        Organs organ = organsRepository.findById(hospital.getSpecializationOrgans().getId()).orElseThrow(() -> new IllegalArgumentException("Organ Not Found"));

        try {

            Queues queue = new Queues();

            queue.setName(name);
            queue.setOrganId(organ);
            queue.setHospitalId(hospital);

            queuesRepository.save(queue);
        }
        catch (DataIntegrityViolationException e) {

            throw new IllegalArgumentException("You Already Have A Queue In Your Hospital!");
        }

        return "You Successfully Created A Queue";
    }

    public Set<Patients> allMyPatients() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return hospital.getPatients();
    }

    public List<QueuesHospitalsPatients> allMyPatientsInTheQueue() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return queuesHospitalsPatientsRepository.findAllByHospitalId(hospital);
    }

    public List<HospitalsOperations> allMyHospitalsOperations() {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        return hospitalsOperationsRepository.findAllByHospitalId(hospital);
    }

    public String appointTransportationOperationByTheQueue(String doctorName, String role, LocalDateTime time, int donorId) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));
        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Does Not Exist!"));

        HospitalsDonorsOrgans hospitalsDonorsOrgans = hospitalsDonorsOrgansRepository.findByHospitalIdAndDonorId(hospital, donor)
                .orElseThrow(() -> new IllegalArgumentException("Donor Not Found In Your Hospital!"));

        QueuesHospitalsPatients queuesHospitalsPatients = queuesHospitalsPatientsRepository.findFirstByHospitalId(hospital);

        if (queuesHospitalsPatients == null) {

            throw new IllegalArgumentException("Patient In The Queue Not Found!");
        }

        return setOperationProperties(doctorName, role, time, hospital, hospitalsDonorsOrgans.getDonorId(), queuesHospitalsPatients);
    }

    public String appointOperationToAnyPatient(String doctorName, String role, LocalDateTime time, int patientId, int donorId) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital!"));

        Donors donor = donorsRepository.findById(donorId).orElseThrow(() -> new IllegalArgumentException("Donor Not Found!"));

        HospitalsDonorsOrgans hospitalsDonorsOrgans = hospitalsDonorsOrgansRepository.findByHospitalIdAndDonorId(hospital, donor)
                .orElseThrow(() -> new IllegalArgumentException("Donor Not Found In Your Hospital!"));

        Patients patient = patientsRepository.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient Not Found!"));

        QueuesHospitalsPatients queuesHospitalsPatients = queuesHospitalsPatientsRepository.findByHospitalIdAndPatientId(hospital, patient);

        if (queuesHospitalsPatients == null) {

            throw new IllegalArgumentException("Patient In The Queue Not Found!");
        }

        return setOperationProperties(doctorName, role, time, hospital, hospitalsDonorsOrgans.getDonorId(), queuesHospitalsPatients);
    }

    private String setOperationProperties(String doctorName, String role, LocalDateTime time, Hospitals hospital,
                                          Donors donor, QueuesHospitalsPatients queuesHospitalsPatients) {

        HospitalsOperations hospitalsOperations = new HospitalsOperations();

        hospitalsOperations.setHospitalId(hospital);
        hospitalsOperations.setPatientId(queuesHospitalsPatients.getPatientId());
        hospitalsOperations.setDonorId(donor);
        hospitalsOperations.setOrganId(donor.getOrganDonates());
        hospitalsOperations.setDoctorName(doctorName);
        hospitalsOperations.setDoctorSpecialization(role);
        hospitalsOperations.setOperationTime(time);

        hospitalsOperationsRepository.save(hospitalsOperations);

        List<QueuesHospitalsPatients> allPatientsInTheQueues = queuesHospitalsPatientsRepository.findAllByPatientId(queuesHospitalsPatients.getPatientId());

        queuesHospitalsPatientsRepository.deleteAll(allPatientsInTheQueues);

        emailService.sendCodeToEmail(donor.getUserId().getEmail(), "Hospital '" + hospital.getName() + "' Has Assigned An Operation To You! Make A Decision To Approve Or Reject The Operation In Your Dashboard");
        emailService.sendCodeToEmail(queuesHospitalsPatients.getPatientId().getUserId().getEmail(), "Hospital '" + hospital.getName() + "' Has Assigned An Operation To You. Go And Check It In Your Dashboard!");

        return "You Successfully Appointed Operation To " + time;
    }

    public String updateOperationProperties(int operationId, Boolean successful, LocalDateTime time,
                                            String doctorName, String doctorRole) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));

        HospitalsOperations hospitalsOperations = hospitalsOperationsRepository.findByIdAndHospitalId(operationId, hospital).orElseThrow(() -> new IllegalArgumentException("Operation Not Found"));

        if (successful != null) {

            hospitalsOperations.setOperationIsSuccessful(successful);
        }

        if (time != null) {

            hospitalsOperations.setOperationTime(time);
        }

        if (doctorName != null) {

            hospitalsOperations.setDoctorName(doctorName);
        }

        if (doctorRole != null) {

            hospitalsOperations.setDoctorSpecialization(doctorRole);
        }

        hospitalsOperationsRepository.save(hospitalsOperations);

        return "You Successfully Updated Operation Details!";
    }

    public String cancelOperation(int operationId) {

        Hospitals hospital = hospitalsRepository.findByCreatorId(USER).orElseThrow(() -> new IllegalArgumentException("You Don't Have A Hospital"));

        HospitalsOperations hospitalsOperations = hospitalsOperationsRepository.findByIdAndHospitalId(operationId, hospital).orElseThrow(() -> new IllegalArgumentException("Operation Not Found"));

        hospitalsOperationsRepository.delete(hospitalsOperations);

        emailService.sendCodeToEmail(hospitalsOperations.getDonorId().getUserId().getEmail(), "Hospital '" + hospital.getName() + "' Has Canceled Your Operation!");
        emailService.sendCodeToEmail(hospitalsOperations.getPatientId().getUserId().getEmail(), "Hospital '" + hospital.getName() + "' Has Canceled Your Operation!");

        return "You Successfully Canceled This Operation!";
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