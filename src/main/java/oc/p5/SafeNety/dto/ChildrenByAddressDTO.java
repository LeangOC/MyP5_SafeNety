package oc.p5.SafeNety.dto;

import java.util.List;

public class ChildrenByAddressDTO {
    private List<ChildDTO> children;
    private List<HouseholdMemberDTO> otherHouseholdMembers;

    public ChildrenByAddressDTO() {}

    public List<ChildDTO> getChildren() { return children; }
    public void setChildren(List<ChildDTO> children) { this.children = children; }

    public List<HouseholdMemberDTO> getOtherHouseholdMembers() { return otherHouseholdMembers; }
    public void setOtherHouseholdMembers(List<HouseholdMemberDTO> otherHouseholdMembers) {
        this.otherHouseholdMembers = otherHouseholdMembers;
    }
}
