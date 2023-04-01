package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum RoleListModel {
    ADMIN(1),
    USER(2);

    private int value;
    RoleListModel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
