package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findAll () ;

    void deleteById(int id ) ;

    @Query(value = "select oi from order_item oi where oi.orders.id = ?1")
    List<OrderItem> findByOrderId(int id );

    OrderItem findById(int id ) ;

}
