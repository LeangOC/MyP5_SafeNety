package oc.p5.SafeNety.dto;

import java.util.List;

public class PersonsAndStationDTO {
    private String stationNumber;
    private List<PersonMedicalInfoDTO> residents;

    public PersonsAndStationDTO() {}

    public String getStationNumber() { return stationNumber; }
    public void setStationNumber(String stationNumber) { this.stationNumber = stationNumber; }

    public List<PersonMedicalInfoDTO> getResidents() { return residents; }
    public void setResidents(List<PersonMedicalInfoDTO> residents) { this.residents = residents; }
}
