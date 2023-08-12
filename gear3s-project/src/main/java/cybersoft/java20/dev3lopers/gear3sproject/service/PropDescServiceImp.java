package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.CategoryProperty;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.PropDescRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.PropDescService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PropDescServiceImp implements PropDescService {
    @Autowired
    PropDescRepository propDescRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean createPropDesc(PropDescCreateDTO propDescCreateDTO) {
        try {
            ProductDesc propDesc = new ProductDesc();
            propDesc.setDescription(propDescCreateDTO.getDesc());
            propDesc.setCategory_property(new CategoryProperty(propDescCreateDTO.getCatePropId()));

            propDescRepository.save(propDesc);
            redisTemplate.delete(RedisModel.PROPDESC.getValue());
            LOGGER.info("Created property description successfully");
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create property description : {}",e.getMessage());
            return false;
        }
    }

    @Override
    public List<PropDescDTO> readAllPropDesc() {
        List<PropDescDTO> propDescDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.PROPDESC.getValue());
            if (data == null){
                List<ProductDesc> propDescList = propDescRepository.findAll();
                for (ProductDesc propDesc : propDescList) {
                    PropDescDTO propDescDto = new PropDescDTO();

                    propDescDto.setId(propDesc.getId());
                    propDescDto.setDesc(propDesc.getDescription());
                    if(propDesc.getCategory_property() != null){
                        propDescDto.setCatePropDTO(new CatePropCreateDTO(propDesc.getCategory_property().getId(),
                                propDesc.getCategory_property().getName(),
                                propDesc.getCategory_property().getCategory().getId()));
                    }

                    propDescDtoList.add(propDescDto);
                }
                redisTemplate.opsForValue().set(RedisModel.PROPDESC.getValue(),gson.toJson(propDescDtoList));
                redisTemplate.expire(RedisModel.PROPDESC.getValue(),30, TimeUnit.MINUTES);
            } else {
                propDescDtoList = gson.fromJson(data, new TypeToken<List<PropDescDTO>>(){}.getType());
            }
            LOGGER.info("Read property description list successfully");
            return propDescDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read property description list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public PropDescDTO readPropDescById(int propDescId) {
        PropDescDTO propDescDto = new PropDescDTO();
        try {
            ProductDesc propDesc = propDescRepository.findById(propDescId);
            if (propDesc == null){
                LOGGER.error("Property description with Id '{}' does not exits",propDescId);
                return null;
            }
            propDescDto.setId(propDesc.getId());
            propDescDto.setDesc(propDesc.getDescription());
            if(propDesc.getCategory_property() != null){
                propDescDto.setCatePropDTO(new CatePropCreateDTO(propDesc.getCategory_property().getId(),
                        propDesc.getCategory_property().getName(),
                        propDesc.getCategory_property().getCategory().getId()));
            }
            LOGGER.info("Read property description info with Id '{}' successfully",propDescId);
            return propDescDto;
        } catch (Exception e){
            LOGGER.error("Failed to read property description info with Id '{}' : {}",propDescId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updatePropDesc(PropDescCreateDTO propDescCreateDTO) {
        try {
            ProductDesc propDesc = propDescRepository.findById(propDescCreateDTO.getId());
            if (propDesc == null){
                LOGGER.error("Property description with Id '{}' does not exits",propDescCreateDTO.getId());
                return false;
            }

            propDesc.setDescription(propDescCreateDTO.getDesc());
            propDesc.setCategory_property(new CategoryProperty(propDescCreateDTO.getCatePropId()));

            propDescRepository.save(propDesc);
            redisTemplate.delete(RedisModel.PROPDESC.getValue());
            LOGGER.info("Updated property description with Id '{}' successfully",propDescCreateDTO.getId());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update property description with Id '{}' : {}",propDescCreateDTO.getId(),e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePropDescById(int propDescId) {
        try {
            propDescRepository.deleteById(propDescId);
            redisTemplate.delete(RedisModel.PROPDESC.getValue());
            LOGGER.info("Deleted property description with Id '{}' successfully",propDescId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete property description with Id '{}' : {}",propDescId,e.getMessage());
            return false;
        }
    }

    @Override
    public List<PropDescCreateDTO> readAllPropDescByCatePropId(int catePropId) {
        List<PropDescCreateDTO> propDescDtoList = new ArrayList<>();

        try {
            List<ProductDesc> propDescList = propDescRepository.findAllByCatePropId(catePropId);
            for (ProductDesc propDesc : propDescList) {
                PropDescCreateDTO propDescDto = new PropDescCreateDTO();

                propDescDto.setId(propDesc.getId());
                propDescDto.setDesc(propDesc.getDescription());
                if(propDesc.getCategory_property() != null){
                    propDescDto.setCatePropId(propDesc.getCategory_property().getId());
                }
                propDescDtoList.add(propDescDto);
            }
            LOGGER.info("Read property description list by category property with Id '{}' successfully",catePropId);
            return propDescDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read property description list by category property with Id '{}' : {}",catePropId,e.getMessage());
            return null;
        }
    }
}
