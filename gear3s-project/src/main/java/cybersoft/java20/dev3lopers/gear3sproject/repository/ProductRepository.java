package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();

    Product findById(int id);

    @Query(value = "select p from  product p WHERE p.name LIKE CONCAT('%',?1,'%' )")
    List<Product> findByName(String name);

    @Query(value = "select p from  product p WHERE p.discountPrice BETWEEN  ?1 AND ?2")
    List<Product> findByOriginPrice(int minPrice, int maxPrice);

    @Query(value = "select p from  product p WHERE p.discountPrice BETWEEN  ?1 AND ?2")
    List<Product> findByDiscountPrice(int minPrice, int maxPrice);

    void deleteById(int id);






//    @Query(value = "insert into product(name, price_origin, price_discount, inventory, sold_qty, description, manufacturer_id, category_id, discount_per, view_qty, create_date) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11",nativeQuery = true)
//    void  insertProduct(String name ,int price_origin,int price_dis , int invetory ,int sold ,String des , int manu_id ,int cate_id ,int dis_per ,int view, String date);


}
