package oc.p5.SafeNety.service;

import oc.p5.SafeNety.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirestationServiceTest {

    private FirestationService service;
    private List<Firestation> testData;

    @BeforeEach
    void setup() {
        testData = new ArrayList<>();
        testData.add(new Firestation("123 Test St", "1"));
        testData.add(new Firestation("456 Test Ave", "2"));
        service = new FirestationService(testData);
    }

    @Test
    void testAddFirestation() {
        Firestation newFs = new Firestation("789 New Rd", "3");
        service.addFirestation(newFs);
        assertTrue(testData.contains(newFs));
    }

    @Test
    void testUpdateFirestation() {
        Firestation update = new Firestation("123 Test St", "5");
        service.updateFirestation(update);
        assertEquals("5", testData.get(0).getStation());
    }

    @Test
    void testDeleteByAddress() {
        service.deleteByAddress("123 Test St");
        assertEquals(1, testData.size());
        assertFalse(testData.stream().anyMatch(f -> f.getAddress().equalsIgnoreCase("123 Test St")));
    }

    @Test
    void testDeleteByStation() {
        service.deleteByStation("2");
        assertEquals(1, testData.size());
        assertFalse(testData.stream().anyMatch(f -> f.getStation().equals("2")));
    }
}
