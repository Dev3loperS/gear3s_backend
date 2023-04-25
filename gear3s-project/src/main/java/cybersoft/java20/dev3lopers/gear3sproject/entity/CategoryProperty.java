package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "category_property")
public class CategoryProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "category_property")
    private Set<ProductDesc> listProdDesc;

    public CategoryProperty() {
    }

    public CategoryProperty(int id) {
        this.id = id;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<ProductDesc> getListProdDesc() {
        return listProdDesc;
    }

    public void setListProdDesc(Set<ProductDesc> listProdDesc) {
        this.listProdDesc = listProdDesc;
    }
}
