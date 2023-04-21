package cybersoft.java20.dev3lopers.gear3sproject.dto;

import io.swagger.models.auth.In;

import java.util.List;

public class PropDescDTO {
    private int propertyId;
    private List<Integer> descId;

    public PropDescDTO() {
    }

    public PropDescDTO(int propertyId, List<Integer> descId) {
        this.propertyId = propertyId;
        this.descId = descId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public List<Integer> getDescId() {
        return descId;
    }

    public void setDescId(List<Integer> descId) {
        this.descId = descId;
    }
}
