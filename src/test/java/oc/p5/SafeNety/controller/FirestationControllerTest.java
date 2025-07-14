package oc.p5.SafeNety.controller;

import org.junit.jupiter.api.Test;
import oc.p5.SafeNety.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;


    @Test
    void testGetPersonsByStation() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("adultCount", 2);
        response.put("childCount", 1);
        response.put("residents", List.of(
                Map.of(
                        "firstName", "John",
                        "lastName", "Boyd",
                        "address", "1509 Culver St",
                        "phone", "841-874-6512"
                )
        ));

        when(alertService.getPersonsCoveredByStation("1")).thenReturn(response);

        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.adultCount").value(2))
                .andExpect(jsonPath("$.childCount").value(1))
                .andExpect(jsonPath("$.residents[0].firstName").value("John"));
    }

}
