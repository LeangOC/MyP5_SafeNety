package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.*;
import oc.p5.SafeNety.model.*;
import oc.p5.SafeNety.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest {

    private AlertService alertService;

    // Mocks manuels
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    void setUp() {
        persons = new ArrayList<>();
        firestations = new ArrayList<>();
        medicalRecords = new ArrayList<>();

        // Adulte
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"));
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984",
                List.of("aznol:350mg"), List.of("nillacilan")));

        // Enfant
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        medicalRecords.add(new MedicalRecord("Tenley", "Boyd", "02/18/2012", List.of(), List.of("peanut")));

        // Caserne
        firestations.add(new Firestation("1509 Culver St", "3"));

        // Service
        alertService = new AlertService(persons, firestations, medicalRecords);
    }

    @Test
    void testGetChildrenByAddress() {
        ChildrenByAddressDTO result = alertService.getChildrenByAddress("1509 Culver St");

        assertEquals(1, result.getChildren().size());
        assertEquals(1, result.getOtherHouseholdMembers().size());

        ChildDTO child = result.getChildren().get(0);
        assertEquals("Tenley", child.getFirstName());
        assertEquals("Boyd", child.getLastName());
        assertTrue(child.getAge() > 0);
    }

    @Test
    void testGetPhonesByStation() {
        List<String> phones = alertService.getPhonesByStation("3");
        assertEquals(List.of("841-874-6512"), phones);
    }

    @Test
    void testGetPersonsWithMedicalInfoByAddress() {
        PersonsAndStationDTO result = alertService.getPersonsWithMedicalInfoByAddress("1509 Culver St");

        assertEquals("3", result.getStationNumber());
        assertEquals(2, result.getResidents().size());

        PersonMedicalInfoDTO resident = result.getResidents().get(0);
        assertNotNull(resident.getFirstName());
        assertTrue(resident.getAge() > 0);
        assertNotNull(resident.getMedications());
    }

    @Test
    void testGetPersonsCoveredByStation() {
        PersonsCoveredByStationDTO result = alertService.getPersonsCoveredByStation("3");

        assertEquals(1, result.getChildCount());
        assertEquals(1, result.getAdultCount());
        assertEquals(2, result.getResidents().size());

        ResidentDTO resident = result.getResidents().get(0);
        assertNotNull(resident.getFirstName());
        assertNotNull(resident.getPhone());
    }

    @Test
    void testGetEmailsByCity() {
        List<String> emails = alertService.getEmailsByCity("Culver");
        assertTrue(emails.contains("john@email.com"));
        assertTrue(emails.contains("tenz@email.com"));
    }

    @Test
    void testGetHouseholdsByStations() {
        HouseholdsByStationDTO result = alertService.getHouseholdsByStations("3");

        assertEquals(1, result.getHouseholds().size());
        assertTrue(result.getHouseholds().containsKey("1509 Culver St"));

        List<PersonMedicalInfoDTO> household = result.getHouseholds().get("1509 Culver St");
        assertEquals(2, household.size());

        PersonMedicalInfoDTO person1 = household.get(0);
        assertNotNull(person1.getFirstName());
        assertTrue(person1.getAge() > 0);
        assertNotNull(person1.getMedications());
    }

    @Test
    void testGetPersonsByLastName() {
        List<PersonInfoDTO> personsByLastName = alertService.getPersonsByLastName("Boyd");

        assertEquals(2, personsByLastName.size());

        PersonInfoDTO person = personsByLastName.get(0);
        assertEquals("Boyd", person.getLastName());
        assertNotNull(person.getFirstName());
        assertTrue(person.getAge() > 0);
        assertNotNull(person.getMedications());
    }
}
