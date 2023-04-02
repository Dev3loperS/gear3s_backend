package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum RoleModel {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String value;
    RoleModel(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
