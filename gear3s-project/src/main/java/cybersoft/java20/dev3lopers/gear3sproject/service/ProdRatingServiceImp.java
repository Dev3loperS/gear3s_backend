package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdRatingDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductRating;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisKeyModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.ProdRatingRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProdRatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProdRatingServiceImp implements ProdRatingService {
    @Value("${uploads.path}")
    private String imagePath;

    @Autowired
    ProdRatingRepository prodRatingRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean createProdRatingByUser(int userId, int prodId, byte star, String comment) {
        try {
            ProductRating rating = new ProductRating();
            rating.setUsers(new Users(userId));
            rating.setProduct(new Product(prodId));
            rating.setStar(star);
            rating.setComment(comment);

            prodRatingRepository.save(rating);
            redisTemplate.delete(RedisKeyModel.RATING.getValue());
            LOGGER.info("Create rating for product with Id '{}' by user has Id '{}' successfully",prodId,userId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create rating for product with Id '{}' by user has Id '{}' : {}",prodId,userId,e.getMessage());
            return false;
        }
    }

    @Override
    public List<ProdRatingDTO> getAllProdRating() {
        List<ProdRatingDTO> resultList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String data = (String) redisTemplate.opsForValue().get(RedisKeyModel.RATING.getValue());
            if(data == null){
                List<ProductRating> prodRatingList = prodRatingRepository.findAll();
                for (ProductRating rating : prodRatingList){
                    ProdRatingDTO ratingDto = new ProdRatingDTO();

                    ratingDto.setRatingId(rating.getId());
                    if(rating.getUsers() != null){
                        ratingDto.setUserId(rating.getUsers().getId());
                        ratingDto.setUserName(rating.getUsers().getFullname());
                        ratingDto.setUserAvatar(imagePath+ImagesModel.AVATAR.getValue()+rating.getUsers().getAvatar());
                    }
                    if(rating.getProduct() != null){
                        ratingDto.setProductId(rating.getProduct().getId());
                        ratingDto.setProductName(rating.getProduct().getName());
                    }
                    ratingDto.setStar(rating.getStar());
                    ratingDto.setComment(rating.getComment());

                    resultList.add(ratingDto);
                }
                redisTemplate.opsForValue().set(RedisKeyModel.RATING.getValue(),gson.toJson(resultList));
                redisTemplate.expire(RedisKeyModel.RATING.getValue(),30, TimeUnit.MINUTES);
            } else {
                resultList = gson.fromJson(data, new TypeToken<List<ProdRatingDTO>>(){}.getType());
            }
            LOGGER.info("Read all product rating list successfully");
            return resultList;
        } catch (Exception e){
            LOGGER.error("Failed to read all product rating list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public ProdRatingDTO getProdRatingById(int ratingId) {
        try {
            ProductRating rating = prodRatingRepository.findById(ratingId);
            if (rating == null){
                LOGGER.error("Product rating with Id '{}' does not exits",ratingId);
                return null;
            }
            ProdRatingDTO ratingDto = new ProdRatingDTO();

            ratingDto.setRatingId(rating.getId());
            if(rating.getUsers() != null){
                ratingDto.setUserId(rating.getUsers().getId());
                ratingDto.setUserName(rating.getUsers().getFullname());
                ratingDto.setUserAvatar(imagePath+ImagesModel.AVATAR.getValue()+rating.getUsers().getAvatar());
            }
            if(rating.getProduct() != null){
                ratingDto.setProductId(rating.getProduct().getId());
                ratingDto.setProductName(rating.getProduct().getName());
            }
            ratingDto.setStar(rating.getStar());
            ratingDto.setComment(rating.getComment());
            LOGGER.info("Read product rating info with Id '{}' successfully",ratingId);
            return ratingDto;
        } catch (Exception e){
            LOGGER.error("Failed to read product rating info with Id '{}' : {}",ratingId,e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProdRatingDTO> getAllRatingOfProd(int productId) {
        List<ProdRatingDTO> resultList = new ArrayList<>();
        try {
            List<ProductRating> prodRatingList = prodRatingRepository.findAllByProductId(productId);
            for (ProductRating rating : prodRatingList){
                ProdRatingDTO ratingDto = new ProdRatingDTO();

                ratingDto.setRatingId(rating.getId());
                if(rating.getUsers() != null){
                    ratingDto.setUserId(rating.getUsers().getId());
                    ratingDto.setUserName(rating.getUsers().getFullname());
                    ratingDto.setUserAvatar(imagePath+ImagesModel.AVATAR.getValue()+rating.getUsers().getAvatar());
                }
                if(rating.getProduct() != null){
                    ratingDto.setProductId(rating.getProduct().getId());
                    ratingDto.setProductName(rating.getProduct().getName());
                }
                ratingDto.setStar(rating.getStar());
                ratingDto.setComment(rating.getComment());

                resultList.add(ratingDto);
            }
            LOGGER.info("Read rating list of product with Id '{}' successfully",productId);
            return resultList;
        } catch (Exception e){
            LOGGER.error("Failed to read rating list of product with Id '{}' : {}",productId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteProdRating(int prodRatingId) {
        try {
            prodRatingRepository.deleteById(prodRatingId);
            redisTemplate.delete(RedisKeyModel.RATING.getValue());
            LOGGER.info("Deleted product rating with Id '{}' successfully",prodRatingId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete product rating with Id '{}' : {}",prodRatingId,e.getMessage());
            return false;
        }
    }
}
