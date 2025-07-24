package oc.p5.SafeNety.service;

import oc.p5.SafeNety.dto.PersonDTO;
import oc.p5.SafeNety.exception.PersonNotFoundException;
import oc.p5.SafeNety.model.Person;
import oc.p5.SafeNety.utils.DataLoader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final List<Person> persons;
    //private final List<Person> persons = DataLoader.loadData().getPersons();

    // Constructeur pour usage normal avec DataLoader
    public PersonService() {this.persons = DataLoader.loadData().getPersons();}

    // ✅ Constructeur pour tests unitaires
    public PersonService(List<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person p) {
        persons.add(p);
        DataLoader.saveData();
    }

    public void updatePerson(Person updated) {
        for (Person p : persons) {
            if (p.getFirstName().equalsIgnoreCase(updated.getFirstName()) &&
                    p.getLastName().equalsIgnoreCase(updated.getLastName())) {
                p.setAddress(updated.getAddress());
                p.setCity(updated.getCity());
                p.setZip(updated.getZip());
                p.setPhone(updated.getPhone());
                p.setEmail(updated.getEmail());
                DataLoader.saveData();
                return;
            }
        }
    }

    public void deletePersonne(PersonDTO personDTO) {
        boolean removed = persons.removeIf(f -> (f.getFirstName().equalsIgnoreCase(personDTO.firstName)) && (f.getLastName().equalsIgnoreCase(personDTO.lastName)));

        if (!removed) {
            throw new PersonNotFoundException(personDTO.firstName, personDTO.lastName);
        }
        DataLoader.saveData();
    }
}
