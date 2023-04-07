package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAll();
    Product findById(int id ) ;
    Product findByName(String name ) ;
    List<Product> findByOriginPrice(int price ) ;
    List<Product> findByDiscountPrice(int price ) ;

    void deleteById(int id ) ;


}
