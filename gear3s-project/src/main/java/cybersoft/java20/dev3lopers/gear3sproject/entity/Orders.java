package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total")
    private long total;

    @Column(name = "shipping_address")
    private String shippingAddress;


    @ManyToOne
    @JoinColumn(name = "user_card_id")
    private UserCard user_card;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private OrderStatus order_status;

    @ManyToOne
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    @OneToMany(mappedBy = "orders")
    private Set<OrderItem> listOrderItem;

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


    public UserCard getUser_card() {
        return user_card;
    }

    public void setUser_card(UserCard user_card) {
        this.user_card = user_card;
    }

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Set<OrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public void setListOrderItem(Set<OrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }
}
