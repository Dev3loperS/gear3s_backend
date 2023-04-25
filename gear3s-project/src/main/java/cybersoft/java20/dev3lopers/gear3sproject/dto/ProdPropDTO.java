package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdPropDTO {
    private int id;
    private ProdNameOnlyDTO productDTO;
    private PropDescDTO propDescDTO;

    public ProdPropDTO() {
    }

    public ProdPropDTO(int id, ProdNameOnlyDTO productDTO, PropDescDTO propDescDTO) {
        this.id = id;
        this.productDTO = productDTO;
        this.propDescDTO = propDescDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProdNameOnlyDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProdNameOnlyDTO productDTO) {
        this.productDTO = productDTO;
    }

    public PropDescDTO getPropDescDTO() {
        return propDescDTO;
    }

    public void setPropDescDTO(PropDescDTO propDescDTO) {
        this.propDescDTO = propDescDTO;
    }
}
