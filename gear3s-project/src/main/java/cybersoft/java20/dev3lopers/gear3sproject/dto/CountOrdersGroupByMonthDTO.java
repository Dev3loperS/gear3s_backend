package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class CountOrdersGroupByMonthDTO {
    private int month ;
    private long totalOrders ;

    public CountOrdersGroupByMonthDTO() {

    }

    public CountOrdersGroupByMonthDTO(int month, long totalOrders) {
        this.month = month;
        this.totalOrders = totalOrders;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int year) {
        this.month = year;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
