package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.controller.FirestationController;
import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationControllerTest {

    @Test
    void testGetFirestationAddresses() {
        FirestationService mockService = mock(FirestationService.class);
        FirestationController controller = new FirestationController(mockService);

        List<FirestationDTO> mockResult = Arrays.asList(
                new FirestationDTO("1509 Culver St"),
                new FirestationDTO("834 Binoc Ave")
        );

        when(mockService.getAddressesByStation("1")).thenReturn(mockResult);

        ResponseEntity<List<FirestationDTO>> response = controller.getFirestationAddresses("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("1509 Culver St", response.getBody().get(0).getAddress());
    }
}
