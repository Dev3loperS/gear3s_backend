package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Category;
import cybersoft.java20.dev3lopers.gear3sproject.entity.CategoryProperty;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.CatePropRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.CatePropService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CatePropServiceImp implements CatePropService {
    @Autowired
    CatePropRepository catePropRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean createCateProp(CatePropCreateDTO catePropCreateDTO) {
        try {
            CategoryProperty categoryProperty = new CategoryProperty();
            categoryProperty.setName(catePropCreateDTO.getName());
            categoryProperty.setCategory(new Category(catePropCreateDTO.getCategoryId()));

            catePropRepository.save(categoryProperty);
            redisTemplate.delete(RedisModel.CATEPROP.getValue());
            LOGGER.info("Created category property successfully");
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create category property : {}",e.getMessage());
            return false;
        }
    }

    @Override
    public List<CatePropDTO> readAllCateProp() {
        List<CatePropDTO> catePropDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.CATEPROP.getValue());
            if (data == null){
                List<CategoryProperty> catePropList = catePropRepository.findAll();
                for (CategoryProperty cateProp : catePropList) {
                    CatePropDTO catePropDto = new CatePropDTO();

                    catePropDto.setId(cateProp.getId());
                    catePropDto.setName(cateProp.getName());
                    if(cateProp.getCategory() != null){
                        catePropDto.setCategoryDTO(new AdCategoryDTO(cateProp.getCategory().getId(),
                                                                     cateProp.getCategory().getName()));
                    }

                    catePropDtoList.add(catePropDto);
                }
                redisTemplate.opsForValue().set(RedisModel.CATEPROP.getValue(),gson.toJson(catePropDtoList));
                redisTemplate.expire(RedisModel.CATEPROP.getValue(),30, TimeUnit.MINUTES);
            } else {
                catePropDtoList = gson.fromJson(data, new TypeToken<List<CatePropDTO>>(){}.getType());
            }
            LOGGER.info("Read category property list successfully");
            return catePropDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read category property list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public CatePropDTO readCatePropById(int catePropId) {
        CatePropDTO catePropDto = new CatePropDTO();
        try {
            CategoryProperty cateProp = catePropRepository.findById(catePropId);
            if (cateProp == null){
                LOGGER.error("Category property with Id '{}' does not exits",catePropId);
                return null;
            }
            catePropDto.setId(cateProp.getId());
            catePropDto.setName(cateProp.getName());
            if(cateProp.getCategory() != null){
                catePropDto.setCategoryDTO(new AdCategoryDTO(cateProp.getCategory().getId(),
                        cateProp.getCategory().getName()));
            }
            LOGGER.info("Read category property info with Id '{}' successfully",catePropId);
            return catePropDto;
        } catch (Exception e){
            LOGGER.error("Failed to read category property info with Id '{}' : {}",catePropId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateCateProp(CatePropCreateDTO catePropCreateDTO) {
        try {
            CategoryProperty categoryProperty = catePropRepository.findById(catePropCreateDTO.getId());
            if (categoryProperty == null){
                LOGGER.error("Category property with Id '{}' does not exits",catePropCreateDTO.getId());
                return false;
            }

            categoryProperty.setName(catePropCreateDTO.getName());
            categoryProperty.setCategory(new Category(catePropCreateDTO.getCategoryId()));

            catePropRepository.save(categoryProperty);
            redisTemplate.delete(RedisModel.CATEPROP.getValue());
            LOGGER.info("Updated category property with Id '{}' successfully",catePropCreateDTO.getId());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update category property with Id '{}' : {}",catePropCreateDTO.getId(),e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCatePropById(int catePropId) {
        try {
            catePropRepository.deleteById(catePropId);
            redisTemplate.delete(RedisModel.CATEPROP.getValue());
            LOGGER.info("Deleted category property with Id '{}' successfully",catePropId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete category property with Id '{}' : {}",catePropId,e.getMessage());
            return false;
        }
    }

    @Override
    public List<FilterCatePropDTO> readProdFilterListByCateId(int categoryId) {
        List<FilterCatePropDTO> filterCatePropDtoList = new ArrayList<>();
        try {
            List<CategoryProperty> catePropList = catePropRepository.findAllByCategoryId(categoryId);
            for (CategoryProperty cateProp:catePropList) {
                FilterCatePropDTO filterCatePropDTO = new FilterCatePropDTO();

                filterCatePropDTO.setId(cateProp.getId());
                filterCatePropDTO.setName(cateProp.getName());
                List<ProductDesc> prodDescList = new ArrayList<>(cateProp.getListProdDesc());

                List<FilterProdDescDTO> proDescDtoList = new ArrayList<>();
                for (ProductDesc prodDesc: prodDescList) {
                    FilterProdDescDTO filterProdDescDTO = new FilterProdDescDTO();
                    filterProdDescDTO.setId(prodDesc.getId());
                    filterProdDescDTO.setDesc(prodDesc.getDescription());
                    proDescDtoList.add(filterProdDescDTO);
                }
                filterCatePropDTO.setProdDescDtoList(proDescDtoList);

                filterCatePropDtoList.add(filterCatePropDTO);
            }
            /*for(int i=0;i<catePropDtoList.size();i++){
                if("Hãng sản xuất".equals(catePropDtoList.get(i).getName())) {
                    if (i != 0) Collections.swap(catePropDtoList, 0, i);;
                    break;
                }
            }*/
            LOGGER.info("Read product filter list by property by category with Id '{}' successfully",categoryId);
            return filterCatePropDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read product filter list by property by category with Id '{}' : {}",categoryId,e.getMessage());
            return null;
        }
    }
}
