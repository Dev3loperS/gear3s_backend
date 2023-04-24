package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.CategoryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatePropRepository extends JpaRepository<CategoryProperty,Integer> {
    List<CategoryProperty> findAllByCategoryId(int categoryId);
    CategoryProperty findById(int catePropId);
}
