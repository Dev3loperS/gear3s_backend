package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class RoleDTO {
    private int id;
    private String name;
    private String desc;

    public RoleDTO() {
    }

    public RoleDTO(int id) {
        this.id = id;
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
