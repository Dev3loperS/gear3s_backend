package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.CategoryProperty;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductProperty;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.ProdPropRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProdPropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProdPropServiceImp implements ProdPropService {
    @Autowired
    ProdPropRepository prodPropRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean createProdProp(ProdPropCreateDTO prodPropCreateDTO) {
        try {
            ProductProperty prodProp = new ProductProperty();
            prodProp.setProduct(new Product(prodPropCreateDTO.getProductId()));
            prodProp.setProduct_desc(new ProductDesc(prodPropCreateDTO.getPropertyId()));

            prodPropRepository.save(prodProp);
            redisTemplate.delete(RedisModel.PRODPROP.getValue());
            LOGGER.info("Created product property successfully");
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create product property : {}",e.getMessage());
            return false;
        }
    }

    @Override
    public List<ProdPropDTO> readAllProdProp() {
        List<ProdPropDTO> prodPropDtoList = new ArrayList<>();
        Gson gson = new Gson();
        //redisTemplate.delete(RedisModel.PRODPROP.getValue());
        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.PRODPROP.getValue());
            if (data == null){
                List<ProductProperty> prodPropList = prodPropRepository.findAll();
                for (ProductProperty prodProp : prodPropList) {
                    ProdPropDTO prodPropDto = new ProdPropDTO();

                    prodPropDto.setId(prodProp.getId());
                    if(prodProp.getProduct() != null){
                        prodPropDto.setProductDTO(new ProdNameOnlyDTO(prodProp.getProduct().getId(),
                                                                      prodProp.getProduct().getName(),
                                                                      prodProp.getProduct().getCategory().getName()));
                    }
                    if(prodProp.getProduct_desc() != null && prodProp.getProduct_desc().getCategory_property() != null){
                        CatePropCreateDTO cateProp =
                                new CatePropCreateDTO(prodProp.getProduct_desc().getCategory_property().getId(),
                                                      prodProp.getProduct_desc().getCategory_property().getName(),
                                                      prodProp.getProduct_desc().getCategory_property().getCategory().getId());

                        prodPropDto.setPropDescDTO(new PropDescDTO(prodProp.getProduct_desc().getId(),
                                                            prodProp.getProduct_desc().getDescription(),cateProp));
                    }
                    prodPropDtoList.add(prodPropDto);
                }
                redisTemplate.opsForValue().set(RedisModel.PRODPROP.getValue(),gson.toJson(prodPropDtoList));
                redisTemplate.expire(RedisModel.PRODPROP.getValue(),30, TimeUnit.MINUTES);
            } else {
                prodPropDtoList = gson.fromJson(data, new TypeToken<List<ProdPropDTO>>(){}.getType());
            }
            LOGGER.info("Read product property list successfully");
            return prodPropDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read product property list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public ProdPropDTO readProdPropById(int prodPropId) {
        ProdPropDTO prodPropDto = new ProdPropDTO();
        try {
            ProductProperty prodProp = prodPropRepository.findById(prodPropId);
            if (prodProp == null){
                LOGGER.error("Product property with Id '{}' does not exits",prodPropId);
                return null;
            }
            prodPropDto.setId(prodProp.getId());
            if(prodProp.getProduct() != null){
                prodPropDto.setProductDTO(new ProdNameOnlyDTO(prodProp.getProduct().getId(),
                        prodProp.getProduct().getName(),
                        prodProp.getProduct().getCategory().getName()));
            }
            if(prodProp.getProduct_desc() != null && prodProp.getProduct_desc().getCategory_property() != null){
                CatePropCreateDTO cateProp =
                        new CatePropCreateDTO(prodProp.getProduct_desc().getCategory_property().getId(),
                                prodProp.getProduct_desc().getCategory_property().getName(),
                                prodProp.getProduct_desc().getCategory_property().getCategory().getId());

                prodPropDto.setPropDescDTO(new PropDescDTO(prodProp.getProduct_desc().getId(),
                        prodProp.getProduct_desc().getDescription(),cateProp));
            }
            LOGGER.info("Read product property info with Id '{}' successfully",prodPropId);
            return prodPropDto;
        } catch (Exception e){
            LOGGER.error("Failed to read product property info with Id '{}' : {}",prodPropId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateProdProp(ProdPropCreateDTO prodPropCreateDTO) {
        try {
            ProductProperty prodProp = prodPropRepository.findById(prodPropCreateDTO.getId());
            if (prodProp == null){
                LOGGER.error("Product property with Id '{}' does not exits",prodPropCreateDTO.getId());
                return false;
            }

            prodProp.setProduct(new Product(prodPropCreateDTO.getProductId()));
            prodProp.setProduct_desc(new ProductDesc(prodPropCreateDTO.getPropertyId()));

            prodPropRepository.save(prodProp);
            redisTemplate.delete(RedisModel.PRODPROP.getValue());
            LOGGER.info("Updated product property with Id '{}' successfully",prodPropCreateDTO.getId());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update product property with Id '{}' : {}",prodPropCreateDTO.getId(),e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProdPropById(int prodPropId) {
        try {
            prodPropRepository.deleteById(prodPropId);
            redisTemplate.delete(RedisModel.PRODPROP.getValue());
            LOGGER.info("Deleted product property with Id '{}' successfully",prodPropId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete product property with Id '{}' : {}",prodPropId,e.getMessage());
            return false;
        }
    }
}
