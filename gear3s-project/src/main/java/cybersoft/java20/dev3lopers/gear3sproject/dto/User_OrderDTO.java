package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.Date;
//Day la DTO User  cho bang Order
public class User_OrderDTO {

    private int id;
    private String email;

    private String fullname;

    private Date birthday;

    private String phone;

    private String address;

    private String avatar;

    private RoleDTO roles;

    private SexDTO sex;

    public User_OrderDTO() {
    }

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

    public RoleDTO getRoles() {
        return roles;
    }

    public void setRoles(RoleDTO roles) {
        this.roles = roles;
    }

    public SexDTO getSex() {
        return sex;
    }

    public void setSex(SexDTO sex) {
        this.sex = sex;
    }
}
