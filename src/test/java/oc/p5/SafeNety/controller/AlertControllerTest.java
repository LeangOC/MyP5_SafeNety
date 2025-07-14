package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.dto.FirestationCoverageDTO;
import oc.p5.SafeNety.dto.ResidentDTO;
import oc.p5.SafeNety.service.AlertService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertControllerTest {

    @InjectMocks
    private AlertController controller;

    @Mock
    private AlertService alertService;

    @Test
    void testGetCoverageByStation() {
        // ✅ DTO simulé
        List<ResidentDTO> residents = List.of(
                new ResidentDTO("John", "Doe", "123 rue Lafayette", "123-456"),
                new ResidentDTO("Emma", "Doe", "123 rue Lafayette", "789-123")
        );

        FirestationCoverageDTO expectedDto = new FirestationCoverageDTO(residents, 1, 1);

        when(alertService.getPersonsCoveredByStation("1")).thenReturn(expectedDto);

        ResponseEntity<FirestationCoverageDTO> response = controller.getCoverageByStation("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getResidents().size());
        assertEquals(1, response.getBody().getAdultCount());
        assertEquals(1, response.getBody().getChildCount());
    }
}
