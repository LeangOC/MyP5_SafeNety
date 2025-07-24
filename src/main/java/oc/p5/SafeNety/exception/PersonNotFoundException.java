package oc.p5.SafeNety.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String firstName, String lastName) {
        super("Aucune personne trouvée avec le nom : " + firstName + " " + lastName);
    }
}
