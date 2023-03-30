package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;

@Entity(name = "product_property")
public class ProductProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cate_property_id")
    private CategoryProperty category_property;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CategoryProperty getCategory_property() {
        return category_property;
    }

    public void setCategory_property(CategoryProperty category_property) {
        this.category_property = category_property;
    }
}
