package oc.p5.SafeNety.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import oc.p5.SafeNety.model.DataWrapper;

import java.io.File;
import java.io.IOException;

public class DataLoader {
    private static DataWrapper data; // contient List<Firestation>
    private static final String FILE_PATH = "src/main/resources/data.json";

    static {
        load();
    }

    public static DataWrapper loadData() {
        return data;
    }

    public static void load() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            data = mapper.readValue(new File(FILE_PATH), DataWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture JSON", e);
        }
    }

    public static void saveData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), data);
        } catch (IOException e) {
            throw new RuntimeException("Erreur de sauvegarde JSON", e);
        }
    }

    public static void setMockData(DataWrapper mockData) {
    }
}
