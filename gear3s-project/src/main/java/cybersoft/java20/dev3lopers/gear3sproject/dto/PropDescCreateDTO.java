package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class PropDescCreateDTO {
    private int id;
    private String desc;
    private int catePropId;

    public PropDescCreateDTO() {
    }

    public PropDescCreateDTO(int id, String desc, int catePropId) {
        this.id = id;
        this.desc = desc;
        this.catePropId = catePropId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCatePropId() {
        return catePropId;
    }

    public void setCatePropId(int catePropId) {
        this.catePropId = catePropId;
    }
}
