package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropDescRepository extends JpaRepository<ProductDesc,Integer> {
    ProductDesc findById(int propDescId);
}
