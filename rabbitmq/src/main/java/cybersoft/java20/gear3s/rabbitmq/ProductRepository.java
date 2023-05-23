package cybersoft.java20.gear3s.rabbitmq;

import cybersoft.java20.gear3s.rabbitmq.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findById(int productId);
}
