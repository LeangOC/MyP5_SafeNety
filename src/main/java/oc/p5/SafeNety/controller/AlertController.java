package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.dto.FirestationCoverageDTO;
import oc.p5.SafeNety.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<FirestationCoverageDTO> getCoverageByStation(@RequestParam String stationNumber) {
        FirestationCoverageDTO response = alertService.getPersonsCoveredByStation(stationNumber);
        return ResponseEntity.ok(response);
    }
}
