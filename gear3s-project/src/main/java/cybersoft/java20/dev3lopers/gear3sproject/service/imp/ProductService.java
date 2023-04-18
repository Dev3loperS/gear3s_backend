package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdFilterDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<ProdFilterDTO> readAllProdOrderByView(int categoryId);
    List<ProdFilterDTO> readAllProdOrderByDate(int categoryId);
    List<ProdFilterDTO> readAllProdOrderBySold(int categoryId);
    List<ProdFilterDTO> readAllProdOrderByPriceDesc(int categoryId);
    List<ProdFilterDTO> readAllProdOrderByPriceAsc(int categoryId);
    List<ProdFilterDTO> readAllProdByPriceRange (int categoryId,int minPrice, int maxPrice) ;
    List<ProdFilterDTO> readAllProdByName(int categoryId, String prodName ) ;
    List<String> readAllProductImage(int productId, int categoryId);
    List<ProductDTO> readAllProducts();
    ProductDTO readProductById (int id ) ;



    boolean deleteProductById(int id ) ;
    boolean updateProductById(int id ,ProductDTO productDTO);
    boolean createProduct (ProductDTO productDTO);




}
