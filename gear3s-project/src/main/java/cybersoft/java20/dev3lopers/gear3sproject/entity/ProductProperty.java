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
    @JoinColumn(name = "prod_desc_id")
    private CategoryProperty product_desc;

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

    public CategoryProperty getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(CategoryProperty product_desc) {
        this.product_desc = product_desc;
    }
}
