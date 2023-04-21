package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ShortProdDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.FilterRequest;

import java.util.List;

public interface ProductService {
    boolean createProduct (ProductDTO productDTO);
    List<ProductDTO> readAllProducts();
    ProductDTO readProductById (int id ) ;
    boolean updateProductById(int id ,ProductDTO productDTO);
    boolean deleteProductById(int id ) ;

    List<String> readAllProductImage(int productId, int categoryId);

    List<ShortProdDTO> readAllProdAfterFilter(FilterRequest request);
    List<ShortProdDTO> readAllProdByName(int categoryId, String prodName ) ;

}
