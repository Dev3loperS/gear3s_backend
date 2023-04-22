package cybersoft.java20.dev3lopers.gear3sproject.repository;

import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdRatingRepository extends JpaRepository<ProductRating,Integer> {
    ProductRating findById(int ratingId);
    List<ProductRating> findAllByProductId(int productId);
}
