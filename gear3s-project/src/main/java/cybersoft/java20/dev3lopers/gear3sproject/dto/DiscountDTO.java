package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.sql.Date;

public class DiscountDTO {
    private int id ;
    private String code ;
    private byte discount_per ;
    private Date start_date ;
    private Date end_date ;
    private String description ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte getDiscount_per() {
        return discount_per;
    }

    public void setDiscount_per(byte discount_per) {
        this.discount_per = discount_per;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
