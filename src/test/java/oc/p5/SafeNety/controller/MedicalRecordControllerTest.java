package oc.p5.SafeNety.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddRecord() throws Exception {
        MedicalRecord record = new MedicalRecord("John", "Doe", "02/02/1995", List.of("med1"), List.of("allergy1"));
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Dossier médical ajouté"));
    }

    @Test
    void testUpdateRecord() throws Exception {
       MedicalRecord record = new MedicalRecord("Jane", "Doe", "02/02/1995", List.of("med2"), List.of("allergy1"));

        mockMvc.perform(put("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andExpect(content().string("Dossier médical mis à jour"));
    }

    @Test
    void testDeleteRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dossier médical supprimé"));
    }
}
