package oc.p5.SafeNety.service;

import oc.p5.SafeNety.model.*;
import oc.p5.SafeNety.utils.AgeUtil;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final List<Person> persons;
    private final List<Firestation> firestations;
    private final List<MedicalRecord> medicalRecords;

    // ✅ Constructeur utilisé automatiquement par Spring Boot
    public AlertService() {
        this.persons = DataLoader.loadData().getPersons();
        this.firestations = DataLoader.loadData().getFirestations();
        this.medicalRecords = DataLoader.loadData().getMedicalrecords();
    }


    // ✅ Constructeur supplémentaire pour les tests unitaires
    public AlertService(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalRecords) {
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

    public Map<String, Object> getChildrenByAddress(String address) {
        List<Map<String, Object>> children = new ArrayList<>();
        List<Map<String, String>> householdMembers = new ArrayList<>();

        for (Person p : persons) {
            if (p.getAddress().equalsIgnoreCase(address)) {
                int age = calculateAge(p);
                if (age <= 18) {
                    Map<String, Object> child = new HashMap<>();
                    child.put("firstName", p.getFirstName());
                    child.put("lastName", p.getLastName());
                    child.put("age", age);
                    children.add(child);
                } else {
                    householdMembers.add(Map.of(
                            "firstName", p.getFirstName(),
                            "lastName", p.getLastName()
                    ));
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("children", children);
        response.put("otherHouseholdMembers", householdMembers);
        return response;
    }

    public List<String> getPhonesByStation(String station) {
        Set<String> addresses = firestations.stream()
                .filter(f -> f.getStation().equals(station))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        return persons.stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<String, Object> getPersonsWithMedicalInfoByAddress(String address) {
        Map<String, Object> result = new HashMap<>();

        String station = firestations.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);

        List<Map<String, Object>> residents = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .map(p -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("firstName", p.getFirstName());
                    data.put("lastName", p.getLastName());
                    data.put("phone", p.getPhone());
                    data.put("age", calculateAge(p));

                    MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
                    if (mr != null) {
                        data.put("medications", mr.getMedications());
                        data.put("allergies", mr.getAllergies());
                    }
                    return data;
                }).collect(Collectors.toList());

        result.put("stationNumber", station);
        result.put("residents", residents);
        return result;
    }

    public Map<String, List<Map<String, Object>>> getHouseholdsByStations(String stationNumbersCSV) {
        List<String> stationNumbers = Arrays.asList(stationNumbersCSV.split(","));

        Set<String> addresses = firestations.stream()
                .filter(f -> stationNumbers.contains(f.getStation()))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        for (String address : addresses) {
            List<Map<String, Object>> household = persons.stream()
                    .filter(p -> p.getAddress().equalsIgnoreCase(address))
                    .map(p -> {
                        Map<String, Object> personData = new HashMap<>();
                        personData.put("firstName", p.getFirstName());
                        personData.put("lastName", p.getLastName());
                        personData.put("phone", p.getPhone());
                        personData.put("age", calculateAge(p));

                        MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
                        if (mr != null) {
                            personData.put("medications", mr.getMedications());
                            personData.put("allergies", mr.getAllergies());
                        }
                        return personData;
                    }).collect(Collectors.toList());

            result.put(address, household);
        }

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



    public List<Map<String, Object>> getPersonsByLastName(String lastName) {
        return persons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("firstName", p.getFirstName());
                    data.put("lastName", p.getLastName());
                    data.put("address", p.getAddress());
                    data.put("email", p.getEmail());
                    data.put("age", calculateAge(p));

                    MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
                    if (mr != null) {
                        data.put("medications", mr.getMedications());
                        data.put("allergies", mr.getAllergies());
                    }
                    return data;
                }).collect(Collectors.toList());
    }

    public List<String> getEmailsByCity(String city) {
        return persons.stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
    }
}
