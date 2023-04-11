package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.AccountDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.AdUserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PasswordDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisKeyModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
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
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean checkLogin(String email, String passwordRaw) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String databasePass = userRepository.getPassByEmail(email);

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
            redisTemplate.delete(RedisKeyModel.USERS.getValue());
            LOGGER.info("Account '{}' has been created successfully",accountDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create account '{}' : {}",accountDTO.getEmail(),e.getMessage());
            return false;
        }
    }

    @Override
    public List<AdUserDTO> readAllUser() {
        List<AdUserDTO> userDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisKeyModel.USERS.getValue());
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
                redisTemplate.opsForValue().set(RedisKeyModel.USERS.getValue(),gson.toJson(userDtoList));
                redisTemplate.expire(RedisKeyModel.USERS.getValue(),30, TimeUnit.MINUTES);
            } else {
                userDtoList = gson.fromJson(data,new TypeToken<List<UserDTO>>(){}.getType());
            }
            LOGGER.info("Read user list successfully");
            return userDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read user list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public UserDTO readUserById(int userId) {
        UserDTO userDto = new UserDTO();

        try {
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
            LOGGER.info("Read user info with Id '{}' successfully by User",userId);
            return userDto;
        } catch (Exception e){
            LOGGER.error("Failed to read user info with Id '{}' by User : {}",userId,e.getMessage());
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

            if (avatarFile != null && !"".equals(avatarFile.getOriginalFilename())){
                if(!defaultAva.equals(user.getAvatar())){
                    fileStorageServiceImp.deleteFile(imagePath+ ImagesModel.AVATAR.getValue()+user.getAvatar());
                }
                fileStorageServiceImp.saveFile(avatarFile,ImagesModel.AVATAR.getValue());
                user.setAvatar(avatarFile.getOriginalFilename());
                LOGGER.info("Avatar of account '{}' has been updated successfully",userDTO.getEmail());
            }

            userRepository.save(user);
            LOGGER.info("Profile of account '{}' has been updated successfully",userDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update account '{}' : {}",userDTO.getEmail(),e.getMessage());
            return false;
        }
    }

    /*@Override
    public boolean updateUserByUser(UserDTO userDTO) {
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

            userRepository.save(user);
            LOGGER.info("Account '{}' has been updated successfully",userDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update account '{}' : {}",userDTO.getEmail(),e.getMessage());
            return false;
        }
    }*/

    /*@Override
    public boolean updateAvatar(int userId, MultipartFile avatarFile) {
        try {
            Users user = userRepository.findById(userId);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",userId);
                return false;
            }
            if (avatarFile == null){
                LOGGER.error("MultipartFile not found");
                return false;
            }
            if(!defaultAva.equals(user.getAvatar())){
                fileStorageServiceImp.deleteFile(imagePath+ ImagesModel.AVATAR.getValue()+user.getAvatar());
            }
            fileStorageServiceImp.saveFile(avatarFile,ImagesModel.AVATAR.getValue());
            user.setAvatar(avatarFile.getOriginalFilename());
            userRepository.save(user);

            LOGGER.info("Avatar of user with Id '{}' has been updated successfully",userId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update avatar of user with Id '{}' : {}",userId,e.getMessage());
            System.out.println(e.getMessage());
        }

        return true;
    }*/

    @Override
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            LOGGER.info("Account with Id '{}' has been deleted successfully",id);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete account with Id '{}' : {}",id,e.getMessage());
            return false;
        }
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
            LOGGER.info("Password of account '{}' has been changed successfully",user.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to change password of account with Id '{}' : {}",userId,e.getMessage());
            return false;
        }
    }


}
