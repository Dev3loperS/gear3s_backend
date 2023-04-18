package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum CategoryModel {
    KEYBOARD(1),
    MOUSE(2),
    LAPTOP(3),
    SPEAKER(4),
    HEADSET(5);

    private int value;
    CategoryModel(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
