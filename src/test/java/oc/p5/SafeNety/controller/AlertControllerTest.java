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
public class AlertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @Test
    void testChildAlert() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("children", List.of(Map.of("firstName", "Tenley", "lastName", "Boyd", "age", 12)));
        response.put("otherHouseholdMembers", List.of(Map.of("firstName", "John", "lastName", "Boyd")));

        when(alertService.getChildrenByAddress("1509 Culver St")).thenReturn(response);

        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.children[0].firstName").value("Tenley"))
                .andExpect(jsonPath("$.otherHouseholdMembers[0].firstName").value("John"));
    }

    @Test
    void testPhoneAlert() throws Exception {
        List<String> phones = List.of("841-874-6512", "841-874-6874");
        when(alertService.getPhonesByStation("3")).thenReturn(phones);

        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("841-874-6512"))
                .andExpect(jsonPath("$[1]").value("841-874-6874"));
    }

    @Test
    void testFireAddressAlert() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("stationNumber", "3");
        response.put("residents", List.of(Map.of(
                "firstName", "John",
                "lastName", "Boyd",
                "phone", "841-874-6512",
                "age", 40,
                "medications", List.of("aznol:350mg"),
                "allergies", List.of("nillacilan")
        )));

        when(alertService.getPersonsWithMedicalInfoByAddress("1509 Culver St")).thenReturn(response);

        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stationNumber").value("3"))
                .andExpect(jsonPath("$.residents[0].firstName").value("John"))
                .andExpect(jsonPath("$.residents[0].medications[0]").value("aznol:350mg"));
    }

    @Test
    void testFloodStationsAlert() throws Exception {
        Map<String, List<Map<String, Object>>> response = new HashMap<>();
        response.put("1509 Culver St", List.of(Map.of(
                "firstName", "John",
                "lastName", "Boyd",
                "phone", "841-874-6512",
                "age", 40,
                "medications", List.of("aznol:350mg"),
                "allergies", List.of("nillacilan")
        )));

        when(alertService.getHouseholdsByStations("3")).thenReturn(response);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['1509 Culver St'][0].firstName").value("John"))
                .andExpect(jsonPath("$.['1509 Culver St'][0].medications[0]").value("aznol:350mg"));
    }
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
    @Test
    void testGetPersonsByLastName() throws Exception {
        List<Map<String, Object>> persons = List.of(
                Map.of(
                        "firstName", "John",
                        "lastName", "Boyd",
                        "address", "1509 Culver St",
                        "email", "jaboyd@email.com",
                        "age", 40,
                        "medications", List.of("aznol:350mg"),
                        "allergies", List.of("nillacilan")
                )
        );

        when(alertService.getPersonsByLastName("Boyd")).thenReturn(persons);

        mockMvc.perform(get("/personInfo")
                        .param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].email").value("jaboyd@email.com"))
                .andExpect(jsonPath("$[0].medications[0]").value("aznol:350mg"));
    }
    @Test
    void testGetEmailsByCity() throws Exception {
        List<String> emails = List.of("jaboyd@email.com", "tenz@email.com");

        when(alertService.getEmailsByCity("Culver")).thenReturn(emails);

        mockMvc.perform(get("/communityEmail")
                        .param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("jaboyd@email.com"))
                .andExpect(jsonPath("$[1]").value("tenz@email.com"));
    }


}
