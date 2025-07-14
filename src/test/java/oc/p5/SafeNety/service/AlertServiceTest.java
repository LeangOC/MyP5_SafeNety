package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.FirestationCoverageDTO;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertServiceTest {

    private AlertService alertService;

    @BeforeEach
    void setUp() {
        // ➤ Données de test
        Person adult = new Person("John", "Doe", "123 Rue Lafayette", "Paris", "75000", "123-456", "john@example.com");
        Person child = new Person("Emma", "Doe", "123 Rue Lafayette", "Paris", "75000", "789-123", "emma@example.com");

        Firestation firestation = new Firestation("123 Rue Lafayette", "1");

        // ➤ Adult: 30 ans, Child: 10 ans
        MedicalRecord adultRecord = new MedicalRecord("John", "Doe", LocalDate.now().minusYears(30).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), List.of(), List.of());
        MedicalRecord childRecord = new MedicalRecord("Emma", "Doe", LocalDate.now().minusYears(10).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), List.of(), List.of());

        // ➤ Initialisation manuelle avec constructeur de test
        alertService = new AlertService(
                List.of(adult, child),
                List.of(firestation),
                List.of(adultRecord, childRecord)
        );
    }

    @Test
    void testGetPersonsCoveredByStation() {
        // ➤ Exécution
        FirestationCoverageDTO result = alertService.getPersonsCoveredByStation("1");

        // ➤ Vérifications
        assertNotNull(result);
        assertEquals(2, result.getResidents().size(), "Il doit y avoir 2 résidents");
        assertEquals(1, result.getAdultCount(), "Il doit y avoir 1 adulte");
        assertEquals(1, result.getChildCount(), "Il doit y avoir 1 enfant");

        // ➤ Vérifie les résidents retournés
        assertTrue(result.getResidents().stream().anyMatch(r -> r.getFirstName().equals("John")));
        assertTrue(result.getResidents().stream().anyMatch(r -> r.getFirstName().equals("Emma")));
    }
}
