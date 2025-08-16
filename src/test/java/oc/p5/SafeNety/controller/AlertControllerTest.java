package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.dto.*;
import oc.p5.SafeNety.service.AlertService;
import org.junit.jupiter.api.Test;
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

    ChildDTO child = new ChildDTO();
    child.setFirstName("Tenley");
    child.setLastName("Boyd");
    child.setAge(12);

    HouseholdMemberDTO member = new HouseholdMemberDTO();
    member.setFirstName("John");
    member.setLastName("Boyd");

    ChildrenByAddressDTO response = new ChildrenByAddressDTO();
    response.setChildren(List.of(child));
    response.setOtherHouseholdMembers(List.of(member));

    // Mock du service avec DTO
    when(alertService.getChildrenByAddress("1509 Culver St")).thenReturn(response);

    // Vérification avec MockMvc
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
        PersonMedicalInfoDTO person = new PersonMedicalInfoDTO();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setPhone("841-874-6512");
        person.setAge(40);
        person.setMedications(List.of("aznol:350mg"));
        person.setAllergies(List.of("nillacilan"));

        PersonsAndStationDTO response = new PersonsAndStationDTO();
        response.setStationNumber("3");
        response.setResidents(List.of(person));

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
        PersonMedicalInfoDTO person = new PersonMedicalInfoDTO();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setPhone("841-874-6512");
        person.setAge(40);
        person.setMedications(List.of("aznol:350mg"));
        person.setAllergies(List.of("nillacilan"));

        HouseholdsByStationDTO response = new HouseholdsByStationDTO();
        response.setHouseholds(Map.of("1509 Culver St", List.of(person)));

        when(alertService.getHouseholdsByStations("3")).thenReturn(response);

        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.households['1509 Culver St'][0].firstName").value("John"))
                .andExpect(jsonPath("$.households['1509 Culver St'][0].medications[0]").value("aznol:350mg"));
    }

    @Test
    void testGetPersonsByStation() throws Exception {
        ResidentDTO resident = new ResidentDTO();
        resident.setFirstName("John");
        resident.setLastName("Boyd");
        resident.setAddress("1509 Culver St");
        resident.setPhone("841-874-6512");

        PersonsCoveredByStationDTO response = new PersonsCoveredByStationDTO();
        response.setAdultCount(2);
        response.setChildCount(1);
        response.setResidents(List.of(resident));

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
        PersonInfoDTO person = new PersonInfoDTO();
        person.setFirstName("John");
        person.setLastName("Boyd");
        person.setAddress("1509 Culver St");
        person.setEmail("jaboyd@email.com");
        person.setAge(40);
        person.setMedications(List.of("aznol:350mg"));
        person.setAllergies(List.of("nillacilan"));

        when(alertService.getPersonsByLastName("Boyd")).thenReturn(List.of(person));

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
