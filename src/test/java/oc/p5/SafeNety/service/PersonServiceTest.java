package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.PersonDTO;
import oc.p5.SafeNety.exception.PersonNotFoundException;
import oc.p5.SafeNety.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService service;
    private List<Person> testData;

    @BeforeEach
    void setup() {
        testData = new ArrayList<>();
        testData.add(new Person("Alice", "Brown", "1 Main St", "City", "12345", "123-456", "alice@example.com"));
        service = new PersonService(testData);
    }

    @Test
    void testAddPerson() {
        Person newPerson = new Person("Bob", "White", "2 Elm St", "City", "54321", "987-654", "bob@example.com");
        service.addPerson(newPerson);
        assertTrue(testData.contains(newPerson));
    }

    @Test
    void testUpdatePerson() {
        Person updated = new Person("Alice", "Brown", "New Address", "New City", "99999", "000-000", "new@mail.com");
        service.updatePerson(updated);
        Person result = testData.get(0);
        assertEquals("New Address", result.getAddress());
        assertEquals("New City", result.getCity());
        assertEquals("99999", result.getZip());
    }

    @Test
    void testDeletePersonne_Success() {
        PersonDTO dto = new PersonDTO();
        dto.firstName = "Alice";
        dto.lastName = "Brown";
        service.deletePersonne(dto);
        assertTrue(testData.isEmpty());
    }

    @Test
    void testDeletePersonne_NotFound() {
        PersonDTO dto = new PersonDTO();
        dto.firstName = "Non";
        dto.lastName = "Existent";
        assertThrows(PersonNotFoundException.class, () -> service.deletePersonne(dto));
    }
}
