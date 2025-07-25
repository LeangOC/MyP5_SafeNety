package oc.p5.SafeNety.controller;

import oc.p5.SafeNety.dto.PersonDTO;
import oc.p5.SafeNety.model.Person;
import oc.p5.SafeNety.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/person")

public class PersonController { private static final Logger logger = LogManager.getLogger(PersonController.class);

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
/*
@DeleteMapping
public ResponseEntity<String> deleteMapping(@RequestBody PersonDTO dto) {
    service.deletePersonne(dto);
    return ResponseEntity.ok("Personne supprimée");
} */
@DeleteMapping
public ResponseEntity<String> deleteMapping(@RequestBody PersonDTO dto) {
    logger.debug("Suppression demandée pour: prénom={}, nom={}", dto.firstName, dto.lastName);

    try {
        service.deletePersonne(dto);
        logger.info("Personne supprimée: prénom={}, nom={}", dto.firstName, dto.lastName);
        return ResponseEntity.ok("Personne supprimée");
    } catch (Exception e) {
        logger.info("ERROR  {}:{} -   Échec suppression : prénom={}, nom={} ({})",
                this.getClass().getSimpleName(),
                new Throwable().getStackTrace()[0].getLineNumber(),
                dto.firstName, dto.lastName, e.getMessage());

        logger.error("Erreur lors de la suppression de la personne: prénom={}, nom={}, erreur={}",
                dto.firstName, dto.lastName, e.getMessage(), e);
        return ResponseEntity.internalServerError().body("Erreur lors de la suppression");
    }
}



}
