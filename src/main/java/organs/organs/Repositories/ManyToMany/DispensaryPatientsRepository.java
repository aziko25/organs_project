package organs.organs.Repositories.ManyToMany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryDonors;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatients;
import organs.organs.Models.ManyToMany.Dispensary.DispensaryPatientsId;
import organs.organs.Models.UserTypes.Dispensary;
import organs.organs.Models.UserTypes.Donors;
import organs.organs.Models.UserTypes.Patients;

import java.util.List;

@Repository
public interface DispensaryPatientsRepository extends JpaRepository<DispensaryPatients, Integer> {

    List<DispensaryPatients> findAllByPatientId(Patients patient);

    List<DispensaryPatients> findAllByPatientIdAndDispensaryId(Patients patient, Dispensary dispensary);

    DispensaryPatients findByPatientIdAndIsActive(Patients patientId, Boolean isActive);
}