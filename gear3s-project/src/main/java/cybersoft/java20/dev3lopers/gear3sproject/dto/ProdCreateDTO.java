package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdCreateDTO {
    private String name;
    private int price_origin;
    private int price_discount ;
    private int inventory ;
    private int categoryId;
    private String description ;

    public ProdCreateDTO() {
    }

    public ProdCreateDTO(String name, int price_origin, int price_discount, int inventory, int categoryId, String description) {
        this.name = name;
        this.price_origin = price_origin;
        this.price_discount = price_discount;
        this.inventory = inventory;
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice_origin() {
        return price_origin;
    }

    public void setPrice_origin(int price_origin) {
        this.price_origin = price_origin;
    }

    public int getPrice_discount() {
        return price_discount;
    }

    public void setPrice_discount(int price_discount) {
        this.price_discount = price_discount;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
