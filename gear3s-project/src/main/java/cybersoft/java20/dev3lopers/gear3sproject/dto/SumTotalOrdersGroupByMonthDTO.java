package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class SumTotalOrdersGroupByMonthDTO {
    private int month   ;
    private long revenue ;

    public SumTotalOrdersGroupByMonthDTO(int month, long revenue) {
        this.month = month;
        this.revenue = revenue;
    }

    public SumTotalOrdersGroupByMonthDTO() {

    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
}
