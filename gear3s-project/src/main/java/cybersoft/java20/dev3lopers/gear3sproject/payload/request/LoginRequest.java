package cybersoft.java20.dev3lopers.gear3sproject.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Chưa nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Chưa nhập password")
    private String password;

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
}
