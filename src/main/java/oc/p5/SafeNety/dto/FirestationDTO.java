package oc.p5.SafeNety.dto;

public class FirestationDTO {
    private String address;
    private String station;

    // Constructors
    public FirestationDTO() {}
    public FirestationDTO(String address, String station) {
        this.address = address;
        this.station = station;
    }

    // Getters and Setters
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStation() { return station; }
    public void setStation(String station) { this.station = station; }
}
