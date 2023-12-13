package organs.organs.Services.Queues;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import organs.organs.Models.ManyToMany.Queues.QueuesHospitalsPatients;
import organs.organs.Models.OrgansAndQueues.Organs;
import organs.organs.Models.OrgansAndQueues.Queues;
import organs.organs.Repositories.ManyToMany.QueuesHospitalsPatientsRepository;
import organs.organs.Repositories.OrgansAndQueues.OrgansRepository;
import organs.organs.Repositories.OrgansAndQueues.QueuesRepository;

@Service
@RequiredArgsConstructor
public class QueuesService {

    private final QueuesRepository queuesRepository;
    private final QueuesHospitalsPatientsRepository queuesHospitalsPatientsRepository;
    private final OrgansRepository organsRepository;


}