package cybersoft.java20.dev3lopers.gear3sproject.payload.request;

import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescDTO;

import java.util.List;

public class FilterRequest {
    private int categoryId;
    private String sortType;
    private int minPrice;
    private int maxPrice;
    private List<PropDescDTO> propDescList;

    public FilterRequest() {
    }

    public FilterRequest(int categoryId, String sortType, int minPrice, int maxPrice, List<PropDescDTO> propDescList) {
        this.categoryId = categoryId;
        this.sortType = sortType;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.propDescList = propDescList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<PropDescDTO> getPropDescList() {
        return propDescList;
    }

    public void setPropDescList(List<PropDescDTO> propDescList) {
        this.propDescList = propDescList;
    }
}
