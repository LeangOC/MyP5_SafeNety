package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationservice;

    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsByStation(@RequestParam String stationNumber) {
        return ResponseEntity.ok(firestationservice.getPersonsCoveredByStation(stationNumber));
    }



}
