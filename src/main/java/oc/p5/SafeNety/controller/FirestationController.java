package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.model.Firestation;
import oc.p5.SafeNety.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    @Autowired
    private FirestationService service;

    @PostMapping
    public ResponseEntity<String> addMapping(@RequestBody Firestation firestation) {
        service.addFirestation(firestation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Mapping ajouté");
    }

    @PutMapping
    public ResponseEntity<String> updateMapping(@RequestBody Firestation firestation) {
        service.updateFirestation(firestation);
        return ResponseEntity.ok("Mapping mis à jour");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMapping(
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String station
    ) {
        if (address != null) {
            service.deleteByAddress(address);
            return ResponseEntity.ok("Mapping supprimé pour l'adresse : " + address);
        } else if (station != null) {
            service.deleteByStation(station);
            return ResponseEntity.ok("Mappings supprimés pour la station : " + station);
        } else {
            return ResponseEntity.badRequest().body("Veuillez fournir 'address' ou 'station'");
        }
    }
}
