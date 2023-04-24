package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdPropDescDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ShortProdDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.FilterRequest;

import java.util.List;

public interface ProductService {
    boolean createProduct (ProdCreateDTO prodCreateDTO);
    List<ProductDTO> readAllProducts();
    ProductDTO readProductById (int productId ) ;
    boolean updateProduct(ProductDTO productDTO);
    boolean deleteProductById(int productId ) ;

    List<ShortProdDTO> readAllProdAfterFilter(FilterRequest request);
    List<ShortProdDTO> readAllProdByName(int categoryId, String prodName ) ;
    boolean confirmOrder(int productId, int requestNum);
    void updateProductView(int productId);
    List<ProdPropDescDTO> readProdPropDescById(int productId);

}
