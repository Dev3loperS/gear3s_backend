package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "user_card")
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mycard_id")
    private MyCard mycard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "user_card")
    private Set<Orders> listOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyCard getMyCard() {
        return mycard;
    }

    public void setMyCard(MyCard myCard) {
        this.mycard = myCard;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<Orders> getListOrder() {
        return listOrder;
    }

    public void setListOrder(Set<Orders> listOrder) {
        this.listOrder = listOrder;
    }
}
