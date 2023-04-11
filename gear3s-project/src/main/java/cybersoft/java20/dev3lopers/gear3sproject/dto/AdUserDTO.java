package cybersoft.java20.dev3lopers.gear3sproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AdUserDTO {
    private int id;
    private String email;
    private String fullname;
    private String birthday;
    private String phone;
    private int roleId;

    public AdUserDTO(){}

    public AdUserDTO(int id, String email, String fullname, String birthday, String phone, int roleId) {
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.birthday = birthday;
        this.phone = phone;
        this.roleId = roleId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
