package cybersoft.java20.dev3lopers.gear3sproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;

public class ShippingDTO {
    @JsonIgnore
    private int id;


    private String name;


    private int price;

    public ShippingDTO() {
    }

    public ShippingDTO(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
