package cybersoft.java20.dev3lopers.gear3sproject.dto;

public class ProdPropCreateDTO {
    private int id;
    private int ProductId;
    private int PropertyId;

    public ProdPropCreateDTO() {
    }

    public ProdPropCreateDTO(int id, int productId, int propertyId) {
        this.id = id;
        ProductId = productId;
        PropertyId = propertyId;
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

    public int getPropertyId() {
        return PropertyId;
    }

    public void setPropertyId(int propertyId) {
        PropertyId = propertyId;
    }
}
