package oc.p5.SafeNety.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        // Ajoute une personne adulte
        persons.add(new Person("John", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "john@email.com"));
        medicalRecords.add(new MedicalRecord("John", "Boyd", "03/06/1984",
                List.of("aznol:350mg"), List.of("nillacilan")));

        // Ajoute un enfant
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenz@email.com"));
        medicalRecords.add(new MedicalRecord("Tenley", "Boyd", "02/18/2012", List.of(), List.of("peanut")));

        // Caserne
        firestations.add(new Firestation("1509 Culver St", "3"));

        // Création du service avec injection manuelle
        alertService = new AlertService(persons, firestations, medicalRecords);
    }

    @Test
    void testGetChildrenByAddress() {
        Map<String, Object> result = alertService.getChildrenByAddress("1509 Culver St");
        List<?> children = (List<?>) result.get("children");
        List<?> others = (List<?>) result.get("otherHouseholdMembers");

        assertEquals(1, children.size());
        assertEquals(1, others.size());

        Map<String, Object> child = (Map<String, Object>) children.get(0);
        assertEquals("Tenley", child.get("firstName"));
    }

    @Test
    void testGetPhonesByStation() {
        List<String> phones = alertService.getPhonesByStation("3");
        assertEquals(List.of("841-874-6512"), phones);
    }

    @Test
    void testGetPersonsWithMedicalInfoByAddress() {
        Map<String, Object> result = alertService.getPersonsWithMedicalInfoByAddress("1509 Culver St");

        assertEquals("3", result.get("stationNumber"));
        List<?> residents = (List<?>) result.get("residents");
        assertEquals(2, residents.size());

        Map<String, Object> resident = (Map<String, Object>) residents.get(0);
        assertTrue(resident.containsKey("firstName"));
        assertTrue(resident.containsKey("age"));
        assertTrue(resident.containsKey("medications"));
    }

    @Test
    void testGetPersonsCoveredByStation() {
        Map<String, Object> result = alertService.getPersonsCoveredByStation("3");

        assertEquals(1, result.get("childCount"));
        assertEquals(1, result.get("adultCount"));
        List<?> residents = (List<?>) result.get("residents");
        assertEquals(2, residents.size());
    }

    @Test
    void testGetEmailsByCity() {
        List<String> emails = alertService.getEmailsByCity("Culver");
        assertTrue(emails.contains("john@email.com"));
        assertTrue(emails.contains("tenz@email.com"));
    }
    @Test
    void testGetHouseholdsByStations() {
        Map<String, List<Map<String, Object>>> result = alertService.getHouseholdsByStations("3");

        assertEquals(1, result.size());
        assertTrue(result.containsKey("1509 Culver St"));

        List<Map<String, Object>> household = result.get("1509 Culver St");
        assertEquals(2, household.size());

        Map<String, Object> person1 = household.get(0);
        assertTrue(person1.containsKey("firstName"));
        assertTrue(person1.containsKey("age"));
        assertTrue(person1.containsKey("medications"));
    }
    @Test
    void testGetPersonsByLastName() {
        List<Map<String, Object>> personsByLastName = alertService.getPersonsByLastName("Boyd");

        assertEquals(2, personsByLastName.size());

        Map<String, Object> person = personsByLastName.get(0);
        assertEquals("Boyd", person.get("lastName"));
        assertTrue(person.containsKey("firstName"));
        assertTrue(person.containsKey("age"));
        assertTrue(person.containsKey("medications"));
    }


}

