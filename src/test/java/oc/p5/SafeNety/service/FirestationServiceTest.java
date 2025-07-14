package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.repository.DataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirestationServiceTest {

    private FirestationService firestationService;

    // Faux repository simulé
    private static class FakeDataRepository extends DataRepository {
        @Override
        public List<Firestation> getFirestations() {
            return Arrays.asList(
                    createFirestation("1509 Culver St", "1"),
                    createFirestation("29 15th St", "2"),
                    createFirestation("834 Binoc Ave", "1")
            );
        }

        private Firestation createFirestation(String address, String station) {
            Firestation f = new Firestation();
            f.setAddress(address);
            f.setStation(station);
            return f;
        }
    }

    @BeforeEach
    void setUp() {
        firestationService = new FirestationService(new FakeDataRepository());
    }

    @Test
    void testGetAddressesByStation_shouldReturnCorrectAddresses() {
        List<FirestationDTO> result = firestationService.getAddressesByStation("1");

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getAddress().equals("1509 Culver St")));
        assertTrue(result.stream().anyMatch(dto -> dto.getAddress().equals("834 Binoc Ave")));
    }

    @Test
    void testGetAddressesByStation_shouldReturnEmptyListForUnknownStation() {
        List<FirestationDTO> result = firestationService.getAddressesByStation("99");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
