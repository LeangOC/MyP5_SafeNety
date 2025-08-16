package oc.p5.SafeNety.controller;
import oc.p5.SafeNety.dto.*;
import oc.p5.SafeNety.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // ✅ DTO
    @GetMapping("/firestation")
    public ResponseEntity<PersonsCoveredByStationDTO> getPersonsByStation(@RequestParam String stationNumber) {
        return ResponseEntity.ok(alertService.getPersonsCoveredByStation(stationNumber));
    }

    // ✅ DTO
    @GetMapping("/childAlert")
    public ResponseEntity<ChildrenByAddressDTO> getChildrenByAddress(@RequestParam String address) {
        return ResponseEntity.ok(alertService.getChildrenByAddress(address));
    }

    // ❌ Simple liste
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhonesByFirestation(@RequestParam String firestation) {
        return ResponseEntity.ok(alertService.getPhonesByStation(firestation));
    }

    // ✅ DTO
    @GetMapping("/fire")
    public ResponseEntity<PersonsAndStationDTO> getPersonsAndStationByAddress(@RequestParam String address) {
        return ResponseEntity.ok(alertService.getPersonsWithMedicalInfoByAddress(address));
    }

    // ✅ DTO
    @GetMapping("/flood/stations")
    public ResponseEntity<HouseholdsByStationDTO> getFloodInfo(@RequestParam("stations") String stations) {
        return ResponseEntity.ok(alertService.getHouseholdsByStations(stations));
    }

    // ✅ DTO
    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonInfoDTO>> getPersonInfoByLastName(@RequestParam String lastName) {
        return ResponseEntity.ok(alertService.getPersonsByLastName(lastName));
    }

    // ❌ Simple liste
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getEmailsByCity(@RequestParam String city) {
        return ResponseEntity.ok(alertService.getEmailsByCity(city));
    }
}
