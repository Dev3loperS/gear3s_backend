package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO findById (int id ) ;
    List<ProductDTO> findByPriceDiscount (int minPrice ,int maxPrice  ) ;
    List<ProductDTO> findByName(String name ) ;
    List<ProductDTO> findByPriceOrigin (int min,int max  ) ;

    boolean deleteProductById(int id ) ;
    boolean updateProductById(int id ,ProductDTO productDTO);
}
