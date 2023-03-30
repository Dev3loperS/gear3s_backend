package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity(name = "mycard")
public class MyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number")
    private String number;

    @Column(name = "name")
    private String name;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "cvv")
    private String cvv;

    @OneToMany(mappedBy = "mycard")
    private Set<UserCard> listUserCard;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Set<UserCard> getListUserCard() {
        return listUserCard;
    }

    public void setListUserCard(Set<UserCard> listUserCard) {
        this.listUserCard = listUserCard;
    }
}
