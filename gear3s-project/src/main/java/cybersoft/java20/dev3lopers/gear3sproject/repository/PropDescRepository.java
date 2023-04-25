package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropDescRepository extends JpaRepository<ProductDesc,Integer> {
    @Query(value = "select pd from product_desc pd where pd.category_property.id = ?1")
    List<ProductDesc> findAllByCatePropId(int catePropId);

    ProductDesc findById(int propDescId);
}
