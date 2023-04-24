package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class CatePropDTO {
    private int id;
    private String name;
    private AdCategoryDTO categoryDTO;

    public CatePropDTO() {
    }

    public CatePropDTO(int id, String name, AdCategoryDTO categoryDTO) {
        this.id = id;
        this.name = name;
        this.categoryDTO = categoryDTO;
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

    public AdCategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(AdCategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
}
