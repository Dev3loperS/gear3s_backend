package cybersoft.java20.dev3lopers.gear3sproject.dto;

import java.util.List;

public class ProdPropDTO {
    private int productId;
    private List<ProdDescDTO> prodDescDtoList;

    public ProdPropDTO() {
    }

    public ProdPropDTO(int productId, List<ProdDescDTO> prodDescDtoList) {
        this.productId = productId;
        this.prodDescDtoList = prodDescDtoList;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public List<ProdDescDTO> getProdDescDtoList() {
        return prodDescDtoList;
    }

    public void setProdDescDtoList(List<ProdDescDTO> prodDescDtoList) {
        this.prodDescDtoList = prodDescDtoList;
    }
}
