package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.List;

public class CatePropFilterDTO {
    private int id;
    private String name;
    private List<ProdDescFilterDTO> prodDescFilterDtoList;

    public CatePropFilterDTO() {
    }

    public CatePropFilterDTO(int id, String name, List<ProdDescFilterDTO> prodDescFilterDtoList) {
        this.id = id;
        this.name = name;
        this.prodDescFilterDtoList = prodDescFilterDtoList;
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

    public List<ProdDescFilterDTO> getProdDescDtoList() {
        return prodDescFilterDtoList;
    }

    public void setProdDescDtoList(List<ProdDescFilterDTO> prodDescFilterDtoList) {
        this.prodDescFilterDtoList = prodDescFilterDtoList;
    }
}
