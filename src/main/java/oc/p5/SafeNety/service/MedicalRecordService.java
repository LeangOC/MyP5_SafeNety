package oc.p5.SafeNety.service;

import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final List<MedicalRecord> records;

    // Constructeur standard (prod)
    public MedicalRecordService() {
        this.records = DataLoader.loadData().getMedicalrecords();
    }

    //private final List<MedicalRecord> records = DataLoader.loadData().getMedicalrecords();

    // ✅ Constructeur pour tests unitaires
    public MedicalRecordService(List<MedicalRecord> records) {
        this.records = records;
    }

    public void addRecord(MedicalRecord r) {
        records.add(r);
        DataLoader.saveData();
    }

    public void updateRecord(MedicalRecord updated) {
        for (MedicalRecord r : records) {
            if (r.getFirstName().equalsIgnoreCase(updated.getFirstName()) &&
                    r.getLastName().equalsIgnoreCase(updated.getLastName())) {
                r.setBirthdate(updated.getBirthdate());
                r.setMedications(updated.getMedications());
                r.setAllergies(updated.getAllergies());
                DataLoader.saveData();
                return;
            }
        }
    }

    public void deleteRecord(String firstName, String lastName) {
        records.removeIf(r -> r.getFirstName().equalsIgnoreCase(firstName)
                && r.getLastName().equalsIgnoreCase(lastName));
        DataLoader.saveData();
    }
}