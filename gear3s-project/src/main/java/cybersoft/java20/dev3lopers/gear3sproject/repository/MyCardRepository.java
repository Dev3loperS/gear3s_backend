package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.MyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCardRepository extends JpaRepository<MyCard,Integer> {

    MyCard findByNumber(String number ) ;


}
