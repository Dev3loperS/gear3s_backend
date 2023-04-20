package cybersoft.java20.dev3lopers.gear3sproject.dto;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Orders;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;

import javax.persistence.*;

public class OrderItemDTO {
    private int id;
    private int amount;
    private int subtotal;
    private OrderDTO order;
    private ProductDTO product;

    public OrderItemDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
