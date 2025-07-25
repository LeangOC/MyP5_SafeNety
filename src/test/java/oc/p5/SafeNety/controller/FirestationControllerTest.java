package oc.p5.SafeNety.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.service.FirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddMapping() throws Exception {
        Firestation firestation = new Firestation("1 rue de Paris", "3");

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Mapping ajouté"));
    }

    @Test
    void testUpdateMapping() throws Exception {
        Firestation firestation = new Firestation("2 rue de Lyon", "4");

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping mis à jour"));
    }

    @Test
    void testDeleteByAddress() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("address", "1 rue de Paris"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mapping supprimé pour l'adresse : 1 rue de Paris"));
    }

    @Test
    void testDeleteByStation() throws Exception {
        mockMvc.perform(delete("/firestation")
                        .param("station", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string("Mappings supprimés pour la station : 3"));
    }

    @Test
    void testDeleteBadRequest() throws Exception {
        mockMvc.perform(delete("/firestation"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Veuillez fournir 'address' ou 'station'"));
    }
}
