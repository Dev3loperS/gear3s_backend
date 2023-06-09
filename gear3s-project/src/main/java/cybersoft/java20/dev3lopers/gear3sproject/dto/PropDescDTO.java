package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class PropDescDTO {
    private int id;
    private String desc;
    private CatePropCreateDTO catePropDTO;

    public PropDescDTO() {
    }

    public PropDescDTO(int id, String desc, CatePropCreateDTO catePropDTO) {
        this.id = id;
        this.desc = desc;
        this.catePropDTO = catePropDTO;
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

    public CatePropCreateDTO getCatePropDTO() {
        return catePropDTO;
    }

    public void setCatePropDTO(CatePropCreateDTO catePropDTO) {
        this.catePropDTO = catePropDTO;
    }
}
