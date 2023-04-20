package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price_origin")
    private int originPrice;

    @Column(name = "price_discount")
    private int discountPrice;

    @Column(name = "inventory")
    private int inventory;

    @Column(name = "sold_qty")
    private int soldQty;

    @Column(name = "description")
    private String description;

//    @Column(name = "discount_per")
//    private byte discount_per;

    @Column(name = "view_qty")
    private int view_qty;


    @Column(name = "create_date")
    private Date create_date;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> listOrderItem;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> listProductImage;

    @OneToMany(mappedBy = "product")
    private Set<ProductProperty> listProdProperty;

    @OneToMany(mappedBy = "product")
    private Set<ProductRating> listProdRating;

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

    public int getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(int originPrice) {
        this.originPrice = originPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(int soldQty) {
        this.soldQty = soldQty;
    }

//    public byte getDiscount_per() {
//        return discount_per;
//    }
//
//    public void setDiscount_per(byte discount_per) {
//        this.discount_per = discount_per;
//    }

    public int getView_qty() {
        return view_qty;
    }

    public void setView_qty(int view_qty) {
        this.view_qty = view_qty;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<OrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public void setListOrderItem(Set<OrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }

    public Set<ProductImage> getListProductImage() {
        return listProductImage;
    }

    public void setListProductImage(Set<ProductImage> listProductImage) {
        this.listProductImage = listProductImage;
    }

    public Set<ProductProperty> getListProdProperty() {
        return listProdProperty;
    }

    public void setListProdProperty(Set<ProductProperty> listProdProperty) {
        this.listProdProperty = listProdProperty;
    }

    public Set<ProductRating> getListProdRating() {
        return listProdRating;
    }

    public void setListProdRating(Set<ProductRating> listProdRating) {
        this.listProdRating = listProdRating;
    }
}
