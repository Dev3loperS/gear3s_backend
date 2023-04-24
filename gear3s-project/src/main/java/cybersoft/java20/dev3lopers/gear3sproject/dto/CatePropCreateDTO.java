package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class CatePropCreateDTO {
    private int id;
    private String propName;
    private int categoryId;

    public CatePropCreateDTO() {
    }

    public CatePropCreateDTO(int id, String propName, int categoryId) {
        this.id = id;
        this.propName = propName;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }
}
