package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum CategoryModel {
    KEYBOARD(1),
    MOUSE(2),
    MONITOR(3),
    HEADSET(4),
    LAPTOP(5),
    SPEAKER(6);

    private int value;
    CategoryModel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
