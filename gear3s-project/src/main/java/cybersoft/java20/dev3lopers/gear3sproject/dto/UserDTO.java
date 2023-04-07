package cybersoft.java20.dev3lopers.gear3sproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class UserDTO {
    private int id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String fullname;
    private Date birthday;
    private String phone;
    private String address;
    private String avatar;
    private int lastPay;
    private RoleDTO role;
    private SexDTO sex;

    public UserDTO(){}

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

    public int getLastPay() {
        return lastPay;
    }

    public void setLastPay(int lastPay) {
        this.lastPay = lastPay;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public SexDTO getSex() {
        return sex;
    }

    public void setSex(SexDTO sex) {
        this.sex = sex;
    }
}
