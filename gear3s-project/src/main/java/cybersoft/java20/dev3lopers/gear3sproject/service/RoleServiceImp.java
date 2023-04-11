package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisKeyModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public List<RoleDTO> readAllRole() {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisKeyModel.ROLES.getValue());
            if (data == null){
                List<Roles> roleList = roleRepository.findAll();

                for (Roles role:roleList) {
                    RoleDTO roleDTO = new RoleDTO();
                    roleDTO.setId(role.getId());
                    roleDTO.setName(role.getName());
                    roleDTO.setDesc(role.getDescription());

                    roleDTOList.add(roleDTO);
                }
                redisTemplate.opsForValue().set(RedisKeyModel.ROLES.getValue(),gson.toJson(roleDTOList));
                redisTemplate.expire(RedisKeyModel.ROLES.getValue(),30, TimeUnit.MINUTES);
            } else {
                roleDTOList = gson.fromJson(data,new TypeToken<List<RoleDTO>>(){}.getType());
            }
            LOGGER.info("Read role list successfully");
            return roleDTOList;
        } catch (Exception e){
            LOGGER.error("Failed to read role list : {}",e.getMessage());
            return null;
        }

    }
}
