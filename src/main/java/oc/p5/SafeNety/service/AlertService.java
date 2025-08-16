package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.*;
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

    public AlertService() {
        this.persons = DataLoader.loadData().getPersons();
        this.firestations = DataLoader.loadData().getFirestations();
        this.medicalRecords = DataLoader.loadData().getMedicalrecords();
    }

    public AlertService(List<Person> persons, List<Firestation> firestations, List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalRecords = medicalRecords;
    }

    // ✅ DTO : /firestation
    public PersonsCoveredByStationDTO getPersonsCoveredByStation(String stationNumber) {
        Set<String> addresses = firestations.stream()
                .filter(f -> f.getStation().equals(stationNumber))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        List<ResidentDTO> residents = new ArrayList<>();
        int adults = 0;
        int children = 0;

        for (Person p : persons) {
            if (addresses.contains(p.getAddress())) {
                int age = calculateAge(p);
                if (age <= 18) children++; else adults++;

                ResidentDTO dto = new ResidentDTO();
                dto.setFirstName(p.getFirstName());
                dto.setLastName(p.getLastName());
                dto.setAddress(p.getAddress());
                dto.setPhone(p.getPhone());
                residents.add(dto);
            }
        }

        PersonsCoveredByStationDTO result = new PersonsCoveredByStationDTO();
        result.setResidents(residents);
        result.setAdultCount(adults);
        result.setChildCount(children);
        return result;
    }

    // ✅ DTO : /childAlert
    public ChildrenByAddressDTO getChildrenByAddress(String address) {
        List<ChildDTO> children = new ArrayList<>();
        List<HouseholdMemberDTO> householdMembers = new ArrayList<>();

        for (Person p : persons) {
            if (p.getAddress().equalsIgnoreCase(address)) {
                int age = calculateAge(p);
                if (age <= 18) {
                    ChildDTO child = new ChildDTO();
                    child.setFirstName(p.getFirstName());
                    child.setLastName(p.getLastName());
                    child.setAge(age);
                    children.add(child);
                } else {
                    HouseholdMemberDTO member = new HouseholdMemberDTO();
                    member.setFirstName(p.getFirstName());
                    member.setLastName(p.getLastName());
                    householdMembers.add(member);
                }
            }
        }

        ChildrenByAddressDTO response = new ChildrenByAddressDTO();
        response.setChildren(children);
        response.setOtherHouseholdMembers(householdMembers);
        return response;
    }

    // ❌ PAS DTO : /phoneAlert → juste une liste
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

    // ✅ DTO : /fire
    public PersonsAndStationDTO getPersonsWithMedicalInfoByAddress(String address) {
        String station = firestations.stream()
                .filter(f -> f.getAddress().equalsIgnoreCase(address))
                .map(Firestation::getStation)
                .findFirst()
                .orElse(null);

        List<PersonMedicalInfoDTO> residents = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .map(this::mapToPersonMedicalInfoDTO)
                .collect(Collectors.toList());

        PersonsAndStationDTO result = new PersonsAndStationDTO();
        result.setStationNumber(station);
        result.setResidents(residents);
        return result;
    }

    // ✅ DTO : /flood/stations
    public HouseholdsByStationDTO getHouseholdsByStations(String stationNumbersCSV) {
        List<String> stationNumbers = Arrays.asList(stationNumbersCSV.split(","));
        Set<String> addresses = firestations.stream()
                .filter(f -> stationNumbers.contains(f.getStation()))
                .map(Firestation::getAddress)
                .collect(Collectors.toSet());

        Map<String, List<PersonMedicalInfoDTO>> households = new HashMap<>();

        for (String address : addresses) {
            List<PersonMedicalInfoDTO> household = persons.stream()
                    .filter(p -> p.getAddress().equalsIgnoreCase(address))
                    .map(this::mapToPersonMedicalInfoDTO)
                    .collect(Collectors.toList());
            households.put(address, household);
        }

        HouseholdsByStationDTO dto = new HouseholdsByStationDTO();
        dto.setHouseholds(households);
        return dto;
    }

    // ✅ DTO : /personInfo
    public List<PersonInfoDTO> getPersonsByLastName(String lastName) {
        return persons.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .map(p -> {
                    PersonInfoDTO dto = new PersonInfoDTO();
                    dto.setFirstName(p.getFirstName());
                    dto.setLastName(p.getLastName());
                    dto.setAddress(p.getAddress());
                    dto.setEmail(p.getEmail());
                    dto.setAge(calculateAge(p));

                    MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
                    if (mr != null) {
                        dto.setMedications(mr.getMedications());
                        dto.setAllergies(mr.getAllergies());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ❌ PAS DTO : /communityEmail → juste une liste
    public List<String> getEmailsByCity(String city) {
        return persons.stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
    }

    // Helpers
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

    private PersonMedicalInfoDTO mapToPersonMedicalInfoDTO(Person p) {
        PersonMedicalInfoDTO dto = new PersonMedicalInfoDTO();
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setPhone(p.getPhone());
        dto.setAge(calculateAge(p));

        MedicalRecord mr = getMedicalRecord(p.getFirstName(), p.getLastName());
        if (mr != null) {
            dto.setMedications(mr.getMedications());
            dto.setAllergies(mr.getAllergies());
        }
        return dto;
    }
}
