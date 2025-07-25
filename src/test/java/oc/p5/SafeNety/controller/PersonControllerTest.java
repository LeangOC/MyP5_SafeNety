package oc.p5.SafeNety.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import oc.p5.SafeNety.dto.PersonDTO;
import oc.p5.SafeNety.model.Person;
import oc.p5.SafeNety.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddPerson() throws Exception {
        Person person = new Person("John", "Doe", "address", "city", "12345", "000-000-0000", "email");

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Personne ajoutée"));
    }

    @Test
    void testUpdatePerson() throws Exception {
        Person person = new Person("Jane", "Doe", "new address", "city", "12345", "000-000-0000", "email");

        mockMvc.perform(put("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(content().string("Personne mise à jour"));
    }

    @Test
    void testDeletePerson() throws Exception {

        PersonDTO dto = new PersonDTO();
        dto.firstName = "Alice";
        dto.lastName = "Doe";


        mockMvc.perform(delete("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Personne supprimée"));
    }
}
