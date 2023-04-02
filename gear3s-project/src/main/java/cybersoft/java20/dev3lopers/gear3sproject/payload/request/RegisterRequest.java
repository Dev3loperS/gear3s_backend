package cybersoft.java20.dev3lopers.gear3sproject.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterRequest {
    @NotBlank(message = "Chưa nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Chưa nhập password")
    private String password;

    @NotBlank(message = "Chưa nhập password")
    private String confirmpass;

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String confirmpass) {
        this.email = email;
        this.password = password;
        this.confirmpass = confirmpass;
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

    public String getConfirmpass() {
        return confirmpass;
    }

    public void setConfirmpass(String confirmpass) {
        this.confirmpass = confirmpass;
    }
}
