package cybersoft.java20.dev3lopers.gear3sproject.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "last_payment")
    private int lastPayment;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;

    @ManyToOne
    @JoinColumn(name = "sex_id")
    private Sex sex;

    @OneToMany(mappedBy = "users")
    private Set<UserCard> listUserCard;

//    @OneToMany(mappedBy = "users")
//    private Set<Orders> listOrder;

    @OneToMany(mappedBy = "users")
    private Set<ProductRating> listProductRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLastPayment() {
        return lastPayment;
    }

    public void setLastPayment(int lastPayment) {
        this.lastPayment = lastPayment;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Set<UserCard> getListUserCard() {
        return listUserCard;
    }

    public void setListUserCard(Set<UserCard> listUserCard) {
        this.listUserCard = listUserCard;
    }

//    public Set<Orders> getListOrder() {
//        return listOrder;
//    }
//
//    public void setListOrder(Set<Orders> listOrder) {
//        this.listOrder = listOrder;
//    }

    public Set<ProductRating> getListProductRating() {
        return listProductRating;
    }

    public void setListProductRating(Set<ProductRating> listProductRating) {
        this.listProductRating = listProductRating;
    }
}
