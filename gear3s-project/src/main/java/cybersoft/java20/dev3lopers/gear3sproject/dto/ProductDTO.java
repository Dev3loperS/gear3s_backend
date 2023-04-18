package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.Date;

public class ProductDTO {
    private int id;
    private String name;
    private int price_origin;
    private int price_discount ;
    private int inventory ;
    private int sold_qty;
    private int view_qty;
    private String createDate;
    private int categoryId;
    private String description ;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, int price_origin, int price_discount, int inventory, int sold_qty, int view_qty, String createDate, int categoryId, String description) {
        this.id = id;
        this.name = name;
        this.price_origin = price_origin;
        this.price_discount = price_discount;
        this.inventory = inventory;
        this.sold_qty = sold_qty;
        this.view_qty = view_qty;
        this.createDate = createDate;
        this.categoryId = categoryId;
        this.description = description;
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

    public int getView_qty() {
        return view_qty;
    }

    public void setView_qty(int view_qty) {
        this.view_qty = view_qty;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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
