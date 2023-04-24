package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdRatingDTO;

import java.util.List;

public interface ProdRatingService {
    boolean createProdRatingByUser(int userId, int prodId, byte star, String comment);
    List<ProdRatingDTO> getAllProdRating();
    ProdRatingDTO getProdRatingById(int prodRatingId);
    List<ProdRatingDTO> getAllRatingOfProd(int productId);
    float getAverageStarOfProd(int productId);
    boolean deleteProdRating(int prodRatingId);
}
