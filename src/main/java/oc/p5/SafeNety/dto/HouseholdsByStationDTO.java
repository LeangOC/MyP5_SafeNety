package oc.p5.SafeNety.dto;

import java.util.List;
import java.util.Map;

public class HouseholdsByStationDTO {
    private Map<String, List<PersonMedicalInfoDTO>> households;

    public HouseholdsByStationDTO() {}

    public Map<String, List<PersonMedicalInfoDTO>> getHouseholds() { return households; }
    public void setHouseholds(Map<String, List<PersonMedicalInfoDTO>> households) { this.households = households; }
}

