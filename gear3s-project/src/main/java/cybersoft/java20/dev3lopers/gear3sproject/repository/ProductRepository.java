package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findAll();
    Product findById(int id ) ;

    @Query(value = "select p from  product p WHERE p.name LIKE CONCAT('%',?1,'%' )")
    List<Product> findByName(String name ) ;
    @Query(value = "select p from  product p WHERE p.discountPrice BETWEEN  ?1 AND ?2")
    List<Product> findByOriginPrice(int minPrice ,int maxPrice   ) ;

    @Query(value = "select p from  product p WHERE p.discountPrice BETWEEN  ?1 AND ?2")
    List<Product> findByDiscountPrice(int minPrice ,int maxPrice  ) ;

    void deleteById(int id ) ;


}
