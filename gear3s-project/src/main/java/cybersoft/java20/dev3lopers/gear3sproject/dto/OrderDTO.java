package cybersoft.java20.dev3lopers.gear3sproject.dto;

import cybersoft.java20.dev3lopers.gear3sproject.entity.OrderStatus;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Shipping;
import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;

import javax.persistence.*;
import java.util.Date;

public class OrderDTO {
    private int id;
    private Date orderDate;
    private long total;
    private String shippingAddress;
//    private UserDTO users;
    private UserCardDTO user_card;
    private ShippingDTO shipping;
    private OrderStatusDTO status ;

    public OrderDTO(int id, Date orderDate, long total, String shippingAddress, UserDTO users, UserCardDTO user_card, ShippingDTO shipping, OrderStatusDTO status) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
        this.shippingAddress = shippingAddress;
//        this.users = users;
        this.user_card = user_card;
        this.shipping = shipping;
        this.status = status;
    }

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

//    public UserDTO getUsers() {
//        return users;
//    }
//
//    public void setUsers(UserDTO users) {
//        this.users = users;
//    }

    public UserCardDTO getUser_card() {
        return user_card;
    }

    public void setUser_card(UserCardDTO user_card) {
        this.user_card = user_card;
    }

    public ShippingDTO getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDTO shipping) {
        this.shipping = shipping;
    }

    public OrderStatusDTO getStatus() {
        return status;
    }

    public void setStatus(OrderStatusDTO status) {
        this.status = status;
    }
}
