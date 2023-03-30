package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> listProduct;

    @OneToMany(mappedBy = "category")
    private Set<CategoryProperty> listCateProperty;

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

    public Set<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(Set<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public Set<CategoryProperty> getListCateProperty() {
        return listCateProperty;
    }

    public void setListCateProperty(Set<CategoryProperty> listCateProperty) {
        this.listCateProperty = listCateProperty;
    }
}
