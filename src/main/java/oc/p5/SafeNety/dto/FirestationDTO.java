package oc.p5.SafeNety.dto;

public class FirestationDTO {

    private String address;

    // Constructeur avec un paramètre
    public FirestationDTO(String address) {
        this.address = address;
    }

    // Constructeur vide (optionnel, utile pour Jackson si désérialisation)
    public FirestationDTO() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
