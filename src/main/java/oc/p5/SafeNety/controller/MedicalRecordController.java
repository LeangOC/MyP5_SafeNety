package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.model.MedicalRecord;
import oc.p5.SafeNety.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService service;

    @PostMapping
    public ResponseEntity<String> addRecord(@RequestBody MedicalRecord record) {
        service.addRecord(record);
        return ResponseEntity.status(201).body("Dossier médical ajouté");
    }

    @PutMapping
    public ResponseEntity<String> updateRecord(@RequestBody MedicalRecord record) {
        service.updateRecord(record);
        return ResponseEntity.ok("Dossier médical mis à jour");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRecord(@RequestParam String firstName, @RequestParam String lastName) {
        service.deleteRecord(firstName, lastName);
        return ResponseEntity.ok("Dossier médical supprimé");
    }
}