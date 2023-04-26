package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class SumTotalOrdersGroupByYearDTO {
    private int year ;
    private long revenue ;

    public SumTotalOrdersGroupByYearDTO(int year, long revenue) {
        this.year = year;
        this.revenue = revenue;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
}
