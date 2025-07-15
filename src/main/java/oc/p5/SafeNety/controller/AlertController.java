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


    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildrenByAddress(@RequestParam String address) {
        return ResponseEntity.ok(alertService.getChildrenByAddress(address));
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<?> getPhonesByFirestation(@RequestParam String firestation) {
        return ResponseEntity.ok(alertService.getPhonesByStation(firestation));
    }

    @GetMapping("/fire")
    public ResponseEntity<?> getPersonsAndStationByAddress(@RequestParam String address) {
        return ResponseEntity.ok(alertService.getPersonsWithMedicalInfoByAddress(address));
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<?> getFloodInfo(@RequestParam("stations") String stations) {
        return ResponseEntity.ok(alertService.getHouseholdsByStations(stations));
    }

    @GetMapping("/personInfo")
    public ResponseEntity<?> getPersonInfoByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(alertService.getPersonsByLastName(lastName));
    }


    @GetMapping("/communityEmail")
    public ResponseEntity<?> getEmailsByCity(@RequestParam String city) {
        return ResponseEntity.ok(alertService.getEmailsByCity(city));
    }


}

