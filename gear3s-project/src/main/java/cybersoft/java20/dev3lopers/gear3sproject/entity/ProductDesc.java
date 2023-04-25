package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "product_desc")
public class ProductDesc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cate_property_id")
    private CategoryProperty category_property;

    @OneToMany(mappedBy = "product_desc")
    private Set<ProductProperty> listProdProperty;

    public ProductDesc() {
    }

    public ProductDesc(int id) {
        this.id = id;
    }

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

    public CategoryProperty getCategory_property() {
        return category_property;
    }

    public void setCategory_property(CategoryProperty category_property) {
        this.category_property = category_property;
    }

    public Set<ProductProperty> getListProdProperty() {
        return listProdProperty;
    }

    public void setListProdProperty(Set<ProductProperty> listProdProperty) {
        this.listProdProperty = listProdProperty;
    }
}
