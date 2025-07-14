package oc.p5.SafeNety.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataWrapper {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;

    // Getters & Setters
    public List<Person> getPersons() { return persons; }
    public void setPersons(List<Person> persons) { this.persons = persons; }

    public List<Firestation> getFirestations() { return firestations; }
    public void setFirestations(List<Firestation> firestations) { this.firestations = firestations; }

    public List<MedicalRecord> getMedicalrecords() { return medicalrecords; }
    public void setMedicalrecords(List<MedicalRecord> medicalrecords) { this.medicalrecords = medicalrecords; }
}
