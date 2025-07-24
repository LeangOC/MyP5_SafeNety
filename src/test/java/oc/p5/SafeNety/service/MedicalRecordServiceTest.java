package oc.p5.SafeNety.service;

import oc.p5.SafeNety.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordServiceTest {

    private MedicalRecordService service;
    private List<MedicalRecord> testData;

    @BeforeEach
    void setup() {
        testData = new ArrayList<>();
        testData.add(new MedicalRecord("John", "Doe", "01/01/1990", List.of("med1"), List.of("pollen")));
        service = new MedicalRecordService(testData);
    }

    @Test
    void testAddRecord() {
        MedicalRecord newRec = new MedicalRecord("Jane", "Smith", "02/02/1995", List.of(), List.of());
        service.addRecord(newRec);
        assertTrue(testData.contains(newRec));
    }

    @Test
    void testUpdateRecord() {
        MedicalRecord updated = new MedicalRecord("John", "Doe", "03/03/2000", List.of("newMed"), List.of("nuts"));
        service.updateRecord(updated);
        MedicalRecord result = testData.get(0);
        assertEquals("03/03/2000", result.getBirthdate());
        assertTrue(result.getMedications().contains("newMed"));
        assertTrue(result.getAllergies().contains("nuts"));
    }

    @Test
    void testDeleteRecord() {
        service.deleteRecord("John", "Doe");
        assertTrue(testData.isEmpty());
    }
}
