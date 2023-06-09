package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class CatePropCreateDTO {
    private int id;
    private String name;
    private int categoryId;

    public CatePropCreateDTO() {
    }

    public CatePropCreateDTO(int id, String name, int categoryId) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
