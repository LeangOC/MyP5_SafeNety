package oc.p5.SafeNety.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.model.Person;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class DataRepository {

    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/data.json");

        JsonNode rootNode = mapper.readTree(file);
        persons = Arrays.asList(mapper.treeToValue(rootNode.get("persons"), Person[].class));
        firestations = Arrays.asList(mapper.treeToValue(rootNode.get("firestations"), Firestation[].class));
        medicalrecords = Arrays.asList(mapper.treeToValue(rootNode.get("medicalrecords"), MedicalRecord[].class));
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }
}
