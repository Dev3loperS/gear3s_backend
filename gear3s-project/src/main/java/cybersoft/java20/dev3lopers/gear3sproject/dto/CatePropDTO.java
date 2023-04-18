package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.List;

public class CatePropDTO {
    private int id;
    private String name;
    private List<ProdDescDTO> prodDescDtoList;

    public CatePropDTO() {
    }

    public CatePropDTO(int id, String name, List<ProdDescDTO> prodDescDtoList) {
        this.id = id;
        this.name = name;
        this.prodDescDtoList = prodDescDtoList;
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

    public List<ProdDescDTO> getProdDescDtoList() {
        return prodDescDtoList;
    }

    public void setProdDescDtoList(List<ProdDescDTO> prodDescDtoList) {
        this.prodDescDtoList = prodDescDtoList;
    }
}
