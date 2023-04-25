package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdPropCreateDTO {
    private int id;
    private int ProductId;
    private int PropDescId;

    public ProdPropCreateDTO() {
    }

    public ProdPropCreateDTO(int id, int productId, int propDescId) {
        this.id = id;
        ProductId = productId;
        PropDescId = propDescId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getPropDescId() {
        return PropDescId;
    }

    public void setPropDescId(int propDescId) {
        PropDescId = propDescId;
    }
}
