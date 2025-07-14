package oc.p5.SafeNety.repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import oc.p5.SafeNety.model.Firestation;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepository {

    private List<Firestation> firestations = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = getClass().getClassLoader().getResourceAsStream("data.json");
            DataWrapper data = mapper.readValue(is, DataWrapper.class);
            firestations = data.getFirestations();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    // Wrapper pour le fichier JSON
    public static class DataWrapper {
        private List<Firestation> firestations;

        public List<Firestation> getFirestations() {
            return firestations;
        }

        public void setFirestations(List<Firestation> firestations) {
            this.firestations = firestations;
        }
    }
}
