package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Integer> {

//    @Query(value = "select  s from shipping s where s.name LIKE  CONCAT('%',?1,'%' )",nativeQuery = true)

    Shipping findByNameLike(String name );
}
