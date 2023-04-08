package cybersoft.java20.dev3lopers.gear3sproject.model;

public enum ImagesModel {
    AVATAR("/avatar/"),
    MOUSE("/mouse/"),
    HEADSET("/headset/"),
    KEYBOARD("/keyboard/"),
    LAPTOP("/laptop/"),
    SPEAKER("/speaker/"),
    MONITOR("/monitor/");

    private String value;
    ImagesModel(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
