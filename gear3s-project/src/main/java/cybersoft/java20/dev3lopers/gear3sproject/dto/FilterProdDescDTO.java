package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class FilterProdDescDTO {
    private int id;
    private String desc;

    public FilterProdDescDTO() {
    }

    public FilterProdDescDTO(int id, String desc) {
        this.id = id;
        this.desc = desc;;
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

}
