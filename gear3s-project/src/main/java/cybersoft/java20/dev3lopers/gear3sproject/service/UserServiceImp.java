package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.AccountDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Value("${uploads.personAvaName}")
    private String avaName;

    @Value("${uploads.path}")
    private String imagePath;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

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
        if(userRepository.countByEmail(email) < 1){
            LOGGER.error("Email '{}' does not exist",email);
            return false;
        }
        LOGGER.info("Email '{}' exists",email);
        return true;
    }

    @Override
    public boolean createUser(AccountDTO accountDTO, boolean byAdmin) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        try {
            Users user = new Users();
            user.setEmail(accountDTO.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(accountDTO.getPassword()));
            user.setAvatar(avaName);
            user.setLastPayment(0);
            if (byAdmin){
                user.setRoles(new Roles(accountDTO.getRoleId()));
            } else {
                user.setRoles(roleRepository.findByName(RoleModel.USER.getValue()));
            }
            userRepository.save(user);
            LOGGER.info("Account '{}' has been created successfully",accountDTO.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create account '{}' : {}",accountDTO.getEmail(),e.getMessage());
            return false;
        }
    }

    @Override
    public List<UserDTO> readAllUser() {
        List<UserDTO> userDtoList = new ArrayList<>();

        try {
            List<Users> usersList = userRepository.findAll();

            for (Users user: usersList) {
                UserDTO userDTO = new UserDTO();

                userDTO.setId(user.getId());
                userDTO.setEmail(user.getEmail());
                userDTO.setPassword(null);
                userDTO.setFullname(user.getFullname());
                userDTO.setBirthday(user.getBirthday());
                userDTO.setPhone(user.getPhone());
                userDTO.setAddress(user.getAddress());
                userDTO.setAvatar(user.getAvatar());
                userDTO.setLastPay(user.getLastPayment());

                if(user.getRoles() != null){
                    userDTO.setRoleId(user.getRoles().getId());
                }
                if(user.getSex() != null){
                    userDTO.setSexId(user.getSex().getId());
                }
                userDtoList.add(userDTO);
            }
            LOGGER.info("Read user list successfully");
            return userDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read user list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public UserDTO readUserById(int id) {
        UserDTO userDto = new UserDTO();

        try {
            Users user = userRepository.findById(id);
            if(user == null){
                LOGGER.error("Account with Id '{}' does not exits",id);
                return null;
            }
            userDto.setId(user.getId());
            userDto.setEmail(user.getEmail());
            userDto.setPassword(null);
            userDto.setFullname(user.getFullname());
            userDto.setBirthday(user.getBirthday());
            userDto.setPhone(user.getPhone());
            userDto.setAddress(user.getAddress());
            userDto.setAvatar(imagePath+ImagesModel.AVATAR.getValue()+user.getAvatar());
            userDto.setLastPay(user.getLastPayment());
            userDto.setRoleId(user.getRoles().getId());
            userDto.setSexId(user.getSex().getId());

            LOGGER.info("Read user info with Id '{}' successfully",id);
            return userDto;
        } catch (Exception e){
            LOGGER.error("Failed to read user info with Id '{}' : {}",id,e.getMessage());
            return null;
        }
    }

    @Override
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
            user.setBirthday(userDTO.getBirthday());
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
    }

    @Override
    public boolean updateUserByAdmin(int userId, int roleId) {
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
            if(!avaName.equals(user.getAvatar())){
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
    }
}
