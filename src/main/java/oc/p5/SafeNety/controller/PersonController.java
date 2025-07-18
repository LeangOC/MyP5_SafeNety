package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.model.Person;
import oc.p5.SafeNety.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;

    @PostMapping
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        service.addPerson(person);
        return ResponseEntity.status(201).body("Personne ajoutée");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {
        service.updatePerson(person);
        return ResponseEntity.ok("Personne mise à jour");
    }

    @DeleteMapping
    public ResponseEntity<String> deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        service.deletePerson(firstName, lastName);
        return ResponseEntity.ok("Personne supprimée");
    }
}
