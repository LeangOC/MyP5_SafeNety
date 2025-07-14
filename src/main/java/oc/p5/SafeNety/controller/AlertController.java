package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    @GetMapping("/firestation")
    public ResponseEntity<?> getPersonsByStation(@RequestParam String stationNumber) {
        return ResponseEntity.ok(alertService.getPersonsCoveredByStation(stationNumber));
    }




}
