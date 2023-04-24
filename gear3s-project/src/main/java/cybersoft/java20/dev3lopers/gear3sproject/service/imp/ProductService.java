package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ShortProdDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.FilterRequest;

import java.util.List;

public interface ProductService {
    boolean createProduct (ProdCreateDTO prodCreateDTO);
    List<ProductDTO> readAllProducts();
    ProductDTO readProductById (int id ) ;
    boolean updateProductById(ProductDTO productDTO);
    boolean deleteProductById(int id ) ;

    List<ShortProdDTO> readAllProdAfterFilter(FilterRequest request);
    List<ShortProdDTO> readAllProdByName(int categoryId, String prodName ) ;
    boolean updateProductSoldQty(int productId, int soldQty);

}
