package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();

    Product findById(int id);

    void deleteById(int id);

    // Filter all product
    @Query(value = "select p from  product p where p.name like concat('%',?1,'%' ) ")
    List<Product> findAllProdByName(String name);

    @Query(value = "select p from  product p where p.discountPrice between  ?1 and ?2")
    List<Product> findAllProdByPriceRange(int minPrice, int maxPrice);

    @Query(value = "select p from product p order by p.view_qty desc ",nativeQuery = false)
    List<Product> findAllProdOrderByViewQty();

    @Query(value = "select p from product p order by p.create_date desc ",nativeQuery = false)
    List<Product> findAllProdOrderByCreateDate();

    @Query(value = "select p from product p order by p.soldQty desc ",nativeQuery = false)
    List<Product> findAllProdOrderBySoldQty();

    @Query(value = "select p from product p order by p.discountPrice desc ",nativeQuery = false)
    List<Product> findAllProdOrderByPriceDesc();

    @Query(value = "select p from product p order by p.discountPrice asc ",nativeQuery = false)
    List<Product> findAllProdOrderByPriceAsc();

    // Filter product in a specific category
    @Query(value = "select p from  product p where p.category.id = ?1 and p.name like concat('%',?2,'%' ) ")
    List<Product> findAllProdByCateIdByName(int categoryId, String prodName);

    @Query(value = "select p from  product p where p.category.id = ?1 and p.discountPrice between  ?2 and ?3")
    List<Product> findAllProdByCateIdByPriceRange(int categoryId, int minPrice, int maxPrice);

    @Query(value = "select p from product p where p.category.id = ?1 order by p.view_qty desc ",nativeQuery = false)
    List<Product> findAllProdByCateIdOrderByViewQty(int categoryId);

    @Query(value = "select p from product p where p.category.id = ?1 order by p.create_date desc ",nativeQuery = false)
    List<Product> findAllProdByCateIdOrderByCreateDate(int categoryId);

    @Query(value = "select p from product p where p.category.id = ?1 order by p.soldQty desc ",nativeQuery = false)
    List<Product> findAllProdByCateIdOrderBySoldQty(int categoryId);

    @Query(value = "select p from product p where p.category.id = ?1 order by p.discountPrice desc ",nativeQuery = false)
    List<Product> findAllProdByCateIdOrderByPriceDesc(int categoryId);

    @Query(value = "select p from product p where p.category.id = ?1 order by p.discountPrice asc ",nativeQuery = false)
    List<Product> findAllProdByCateIdOrderByPriceAsc(int categoryId);





}
