package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "select c from category c")
    List<Category> getAllCategory ();
    Category findById(int id ) ;
    Category findByName(String name ) ;



    @Query(value = "delete from category c where c.id =?1",nativeQuery = true)
    boolean deleteById(int id ) ;




}
