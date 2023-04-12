package cybersoft.java20.dev3lopers.gear3sproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class MyCardDTO {
    private int id;
    @NotBlank(message = "Card number can not be blank")
    @Size(min = 16,max = 16)
    private String number;
    @NotBlank(message = "Card name can not be blank")
    private String name;
    @NotBlank(message = "Expiry date can not be blank")
    @DateTimeFormat(pattern = "MM-yyyy")
    private String expiryDate;
    @NotBlank(message = "CVV number can not be blank")
    @Size(min = 3,max = 3)
    private String cvv;

    public MyCardDTO() {
    }

    public MyCardDTO(int id, String number, String name, String expiryDate, String cvv) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
