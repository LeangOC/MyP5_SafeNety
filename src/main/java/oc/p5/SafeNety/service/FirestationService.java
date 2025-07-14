package oc.p5.SafeNety.service;
import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.model.Person;
import oc.p5.SafeNety.repository.DataRepository;
import oc.p5.SafeNety.utils.AgeUtil;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FirestationService {

    private final List<Person> persons;
    private final List<Firestation> firestations;
    private final List<MedicalRecord> medicalRecords;

    // ✅ Constructeur utilisé automatiquement par Spring Boot
    public FirestationService() {
        this.persons = DataLoader.loadData().getPersons();
        this.firestations = DataLoader.loadData().getFirestations();
        this.medicalRecords = DataLoader.loadData().getMedicalrecords();
    }

    // ✅ Constructeur supplémentaire pour les tests unitaires
    public FirestationService(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalRecords = medicalRecords;
    }

    public Map<String, Object> getPersonsCoveredByStation(String stationNumber) {
        Set<String> addresses = firestations.stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        List<Map<String, String>> residents = new ArrayList<>();
        int adults = 0;
        int children = 0;

        for (Person p : persons) {
            if (addresses.contains(p.getAddress())) {
                int age = calculateAge(p);
                if (age <= 18) {
                    children++;
                } else {
                    adults++;
                }
                residents.add(Map.of(
                        "firstName", p.getFirstName(),
                        "lastName", p.getLastName(),
                        "address", p.getAddress(),
                        "phone", p.getPhone()
                ));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("residents", residents);
        result.put("adultCount", adults);
        result.put("childCount", children);
        return result;
    }

    private MedicalRecord getMedicalRecord(String firstName, String lastName) {
        return medicalRecords.stream()
                .filter(m -> m.getFirstName().equalsIgnoreCase(firstName)
                        && m.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
    private int calculateAge(Person p) {
        MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
        if (mr == null || mr.getBirthdate() == null) return -1;
        return AgeUtil.calculateAgeFromBirthdate(mr.getBirthdate());
    }
}
