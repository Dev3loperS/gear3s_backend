package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository  extends JpaRepository<OrderStatus,Integer> {
//    @Query(value = "select  o from order_status o where o.name LIKE  CONCAT('%',?1,'%' )",nativeQuery = true)
    OrderStatus findByNameLike(String name ) ;
}
