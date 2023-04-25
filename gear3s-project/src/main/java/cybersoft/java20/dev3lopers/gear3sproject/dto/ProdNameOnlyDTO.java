package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdNameOnlyDTO {
    private int id;
    private String name;
    private String categoryName;

    public ProdNameOnlyDTO() {
    }

    public ProdNameOnlyDTO(int id, String name, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryName = categoryName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
