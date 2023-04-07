package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProductDTO {
    private int id;
    private String name;
    private int price_origin;
    private int price_discount ;
    private int inventory ;
    private int sold_qty;
    private String description ;
    private DiscountDTO discountDTO ;
    private ManufacturerDTO manufacturerDTO ;
    private CategoryDTO categoryDTO ;

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

    public int getSold_qty() {
        return sold_qty;
    }

    public void setSold_qty(int sold_qty) {
        this.sold_qty = sold_qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public void setDiscountDTO(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

    public ManufacturerDTO getManufacturerDTO() {
        return manufacturerDTO;
    }

    public void setManufacturerDTO(ManufacturerDTO manufacturerDTO) {
        this.manufacturerDTO = manufacturerDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
}
