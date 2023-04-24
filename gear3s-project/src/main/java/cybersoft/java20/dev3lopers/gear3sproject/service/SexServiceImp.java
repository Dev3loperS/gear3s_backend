package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.SexDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.SexRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.SexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SexServiceImp implements SexService {
    @Autowired
    SexRepository sexRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<SexDTO> readAllSex() {
        List<SexDTO> sexDTOList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.SEXES.getValue());
            if (data == null){
                List<Sex> sexList = sexRepository.findAll();

                for (Sex sex : sexList) {
                    SexDTO sexDTO = new SexDTO();
                    sexDTO.setId(sex.getId());
                    sexDTO.setName(sex.getName());

                    sexDTOList.add(sexDTO);
                }
                redisTemplate.opsForValue().set(RedisModel.SEXES.getValue(),gson.toJson(sexDTOList));
                redisTemplate.expire(RedisModel.SEXES.getValue(),30, TimeUnit.MINUTES);
            } else {
                sexDTOList = gson.fromJson(data,new TypeToken<List<SexDTO>>(){}.getType());
            }
            LOGGER.info("Read sex list successfully");
            return sexDTOList;
        } catch (Exception e){
            LOGGER.error("Failed to read sex list : {}",e.getMessage());
            return null;
        }



    }
}
