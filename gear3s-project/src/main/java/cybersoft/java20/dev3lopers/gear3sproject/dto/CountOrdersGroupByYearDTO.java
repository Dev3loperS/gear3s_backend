package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class CountOrdersGroupByYearDTO {
    private int year ;
    private long totalOrders ;

    public CountOrdersGroupByYearDTO() {

    }

    public CountOrdersGroupByYearDTO(int year, long total) {
        this.year = year;
        this.totalOrders = total;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int total) {
        this.totalOrders = total;
    }
}
