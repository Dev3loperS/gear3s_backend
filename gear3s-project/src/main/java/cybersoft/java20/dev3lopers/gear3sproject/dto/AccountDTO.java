package cybersoft.java20.dev3lopers.gear3sproject.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AccountDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private int lastPay;
    private int roleId;

    public AccountDTO() {
    }

    public AccountDTO(String email, String password, int lastPay, int roleId) {
        this.email = email;
        this.password = password;
        this.lastPay = lastPay;
        this.roleId = roleId;
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

    public int getLastPay() {
        return lastPay;
    }

    public void setLastPay(int lastPay) {
        this.lastPay = lastPay;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
