package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.AccountDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.AdUserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PasswordDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.entity.UserCard;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserCardRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImp implements UserService {
    @Value("${uploads.defaultAvatar}")
    private String defaultAva;

    @Value("${uploads.path}")
    private String imagePath;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    @Autowired
    UserCardRepository userCardRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean checkLogin(String email, String passwordRaw) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String databasePass = userRepository.getPassByEmail(email);
        redisTemplate.delete(RedisModel.USER.getValue());
        if(!bCryptPasswordEncoder.matches(passwordRaw,databasePass)){
            LOGGER.error("Login failed with email: {}",email);
            return false;
        }
        LOGGER.info("Login successful with email: {}",email);
        return true;
    }

    @Override
    public boolean checkEmailExistence(String email) {
        if(userRepository.countByEmail(email) > 0){
            LOGGER.error("Email '{}' already exists in database",email);
            return false;
        }
        LOGGER.info("Email '{}' does not exist in database. OK to create this email",email);
        return true;
    }

    @Override
    public boolean createUser(AccountDTO accountDTO, boolean byAdmin) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        try {
            Users user = new Users();
            user.setEmail(accountDTO.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
            user.setAvatar(defaultAva);
            user.setLastPayment(0);
            if (byAdmin){
                user.setRoles(new Roles(accountDTO.getRoleId()));
            } else {
                user.setRoles(roleRepository.findByName(RoleModel.USER.getValue()));
            }
            userRepository.save(user);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Created new account '{}' successfully",accountDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create new account '{}' : {}",accountDTO.getEmail(),e.getMessage());
            return false;
        }
    }

    @Override
    public List<AdUserDTO> readAllUser() {
        List<AdUserDTO> userDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.ALLUSERS.getValue());
            if (data == null){
                List<Users> usersList = userRepository.findAll();

                for (Users user: usersList) {
                    AdUserDTO userDto = new AdUserDTO();

                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setFullname(user.getFullname());
                    if(user.getBirthday() != null){
                        userDto.setBirthday(new SimpleDateFormat("dd-MM-yyyy").format(user.getBirthday()));
                    }
                    userDto.setPhone(user.getPhone());
                    if(user.getRoles() != null){
                        userDto.setRoleId(user.getRoles().getId());
                    }
                    userDtoList.add(userDto);
                }
                redisTemplate.opsForValue().set(RedisModel.ALLUSERS.getValue(),gson.toJson(userDtoList));
                redisTemplate.expire(RedisModel.ALLUSERS.getValue(),30, TimeUnit.MINUTES);
            } else {
                userDtoList = gson.fromJson(data,new TypeToken<List<AdUserDTO>>(){}.getType());
            }
            LOGGER.info("Read user list successfully");
            return userDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read user list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public UserDTO readUserByIdByUser(int userId) {
        UserDTO userDto = new UserDTO();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.USER.getValue());
            if (data == null){
                Users user = userRepository.findById(userId);
                if(user == null){
                    LOGGER.error("Account with Id '{}' does not exits",userId);
                    return null;
                }
                userDto.setId(user.getId());
                userDto.setEmail(user.getEmail());
                userDto.setPassword("**********");
                userDto.setFullname(user.getFullname());
                if(user.getBirthday() != null){
                    userDto.setBirthday(new SimpleDateFormat("dd-MM-yyyy").format(user.getBirthday()));
                }
                userDto.setPhone(user.getPhone());
                userDto.setAddress(user.getAddress());
                userDto.setAvatar(imagePath+ImagesModel.AVATAR.getValue()+user.getAvatar());
                userDto.setLastPay(user.getLastPayment());
                if(user.getRoles() != null){
                    userDto.setRoleId(user.getRoles().getId());
                }
                if(user.getSex() != null){
                    userDto.setSexId(user.getSex().getId());
                }
                redisTemplate.opsForValue().set(RedisModel.USER.getValue(),gson.toJson(userDto));
                redisTemplate.expire(RedisModel.USER.getValue(),30, TimeUnit.MINUTES);
            } else {
                userDto = gson.fromJson(data,new TypeToken<UserDTO>(){}.getType());
            }
            LOGGER.info("Read user info with Id '{}' by User successfully",userId);
            return userDto;
        } catch (Exception e){
            LOGGER.error("Failed to read user info with Id '{}' by User : {}",userId,e.getMessage());
            return null;
        }
    }

    @Override
    public AdUserDTO readUserByIdByAdmin(int userId) {
        AdUserDTO userDto = new AdUserDTO();

        try {
            Users user = userRepository.findById(userId);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userId);
                return null;
            }
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setFullname(user.getFullname());
            if(user.getBirthday() != null){
                userDto.setBirthday(new SimpleDateFormat("dd-MM-yyyy").format(user.getBirthday()));
            }
            userDto.setPhone(user.getPhone());
            if(user.getRoles() != null){
                userDto.setRoleId(user.getRoles().getId());
            }
            LOGGER.info("Read user info with Id '{}' by Admin successfully ",userId);
            return userDto;
        } catch (Exception e){
            LOGGER.error("Failed to read user info with Id '{}' by Admin : {}",userId,e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateUserRoleByAdmin(int userId, int roleId) {
        try {
            Users user = userRepository.findById(userId);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userId);
                return false;
            }
            user.setRoles(new Roles(roleId));

            userRepository.save(user);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Role of account with Id '{}' has been changed successfully",userId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to change role of account with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUserByUser(UserDTO userDTO, MultipartFile avatarFile) {
        try {
            Users user = userRepository.findById(userDTO.getId());
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userDTO.getId());
                return false;
            }
            if(!userDTO.getEmail().equals(user.getEmail())){
                user.setEmail(userDTO.getEmail());
            }
            user.setFullname(userDTO.getFullname());
            user.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(userDTO.getBirthday()));
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
            user.setSex(new Sex(userDTO.getSexId()));

            if(uploadAvatarForUser(user.getId(),user.getAvatar(),avatarFile)){
                user.setAvatar(avatarFile.getOriginalFilename());
            }
            userRepository.save(user);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Profile of account '{}' has been updated successfully",userDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update account '{}' : {}",userDTO.getEmail(),e.getMessage());
            return false;
        }
    }

    private boolean uploadAvatarForUser(int userId, String userAvatar, MultipartFile file){
        if (file != null && !"".equals(file.getOriginalFilename())){
            if(!defaultAva.equals(userAvatar)){
                fileStorageServiceImp.deleteFile(imagePath+ImagesModel.AVATAR.getValue()+userAvatar);
            }
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            if(fileStorageServiceImp.uploadAvatar(userId,file)){
                LOGGER.info("Uploaded avatar of account with Id '{}' successfully",userId);
                return true;
            } else {
                LOGGER.error("Failed to upload avatar of account with Id '{}' ",userId);
                return false;
            }
        } else return false;
    }

    @Override
    public boolean updateUserPassword(int userId, PasswordDTO passwordDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        try {
            Users user = userRepository.findById(userId);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userId);
                return false;
            }
            if(!bCryptPasswordEncoder.matches(passwordDTO.getCurrentPassword(),user.getPassword())){
                LOGGER.error("The current password of account '{}' is incorrect",user.getEmail());
                return false;
            }
            if(!passwordDTO.getConfirmPassword().equals(passwordDTO.getNewPassword())){
                LOGGER.error("The confirmation password of account '{}' does not match",user.getEmail());
                return false;
            }
            user.setPassword(bCryptPasswordEncoder.encode(passwordDTO.getNewPassword()));
            userRepository.save(user);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Password of account '{}' has been changed successfully",user.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to change password of account with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Account with Id '{}' has been deleted successfully",id);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete account with Id '{}' : {}",id,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateLastPay(int userId, int userCardId) {
        try {
            Users user = userRepository.findById(userId);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userId);
                return false;
            }
            UserCard userCard = userCardRepository.findById(userCardId);
            if(userCard.getUsers().getId() != userId){
                LOGGER.error("UserCard with Id '{}' is not belong to account has Id '{}'",userCardId,userId);
                return false;
            }
            user.setLastPayment(userCardId);

            userRepository.save(user);
            redisTemplate.delete(RedisModel.ALLUSERS.getValue());
            redisTemplate.delete(RedisModel.USER.getValue());
            LOGGER.info("Updated last payment Id of account with Id '{}' successfully",userId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to updated last payment Id of account with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }


}
