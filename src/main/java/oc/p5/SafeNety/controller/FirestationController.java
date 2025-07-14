package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.dto.FirestationDTO;
import oc.p5.SafeNety.service.FirestationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<List<FirestationDTO>> getFirestationAddresses(@RequestParam String stationNumber) {
        List<FirestationDTO> addresses = firestationService.getAddressesByStation(stationNumber);
        return ResponseEntity.ok(addresses);
    }
}
