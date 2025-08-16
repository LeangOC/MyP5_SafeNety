package oc.p5.SafeNety.dto;

import java.util.List;

public class PersonsCoveredByStationDTO {
    private List<ResidentDTO> residents;
    private int adultCount;
    private int childCount;

    public PersonsCoveredByStationDTO() {}

    public List<ResidentDTO> getResidents() { return residents; }
    public void setResidents(List<ResidentDTO> residents) { this.residents = residents; }

    public int getAdultCount() { return adultCount; }
    public void setAdultCount(int adultCount) { this.adultCount = adultCount; }

    public int getChildCount() { return childCount; }
    public void setChildCount(int childCount) { this.childCount = childCount; }
}
