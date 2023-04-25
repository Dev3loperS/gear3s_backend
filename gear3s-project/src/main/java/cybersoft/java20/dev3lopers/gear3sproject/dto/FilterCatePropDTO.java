package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.List;

public class FilterCatePropDTO {
    private int id;
    private String name;
    private List<FilterProdDescDTO> filterProdDescDtoList;

    public FilterCatePropDTO() {
    }

    public FilterCatePropDTO(int id, String name, List<FilterProdDescDTO> filterProdDescDtoList) {
        this.id = id;
        this.name = name;
        this.filterProdDescDtoList = filterProdDescDtoList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FilterProdDescDTO> getProdDescDtoList() {
        return filterProdDescDtoList;
    }

    public void setProdDescDtoList(List<FilterProdDescDTO> filterProdDescDtoList) {
        this.filterProdDescDtoList = filterProdDescDtoList;
    }
}
