package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.SexDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.RoleRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @Override
    public boolean checkLogin(String email, String passwordRaw) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String databasePass = userRepository.getPassByEmail(email);

        if(!bCryptPasswordEncoder.matches(passwordRaw,databasePass)){
            LOGGER.error("Login failed with account: {}",email);
            return false;
        }

        return true;
    }

    @Override
    public boolean checkEmailExistence(String email) {
        if(userRepository.countByEmail(email) < 1){
            LOGGER.error("Account '{}' does not exist",email);
            return false;
        }
        return true;
    }

    @Override
    public boolean createUserByAdmin(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFullname(userDTO.getFullname());
        user.setBirthday(userDTO.getBirthday());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setLastPayment(0);
        user.setRoles(new Roles(userDTO.getRole().getId()));
        user.setSex(new Sex(userDTO.getSex().getId()));

        try {
            userRepository.save(user);
            LOGGER.info("Account '{}' has been created successfully by Admin",user.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create account '{}' by Admin: {}",user.getEmail(),e.getMessage());
            return false;
        }
    }

    // Sử dụng cho chức năng Register từ trang người dùng
    @Override
    public boolean createUserByUser(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setLastPayment(0);
        user.setRoles(roleRepository.findByName(RoleModel.USER.getValue()));

        try {
            userRepository.save(user);
            LOGGER.info("Account '{}' has been created successfully by User",user.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create account '{}' by User: {}",user.getEmail(),e.getMessage());
            return false;
        }
    }

    @Override
    public List<UserDTO> readUser(boolean getAllUser,int id) {
        List<UserDTO> userDtoList = new ArrayList<>();
        List<Users> usersList = new ArrayList<>();
        if(getAllUser){
            usersList = userRepository.findAll();
        } else {
            Users users = userRepository.findById(id);
            usersList.add(users);
        }

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
                userDTO.setRole(new RoleDTO(user.getRoles().getName()));
            }
            if(user.getSex() != null){
                userDTO.setSex(new SexDTO(user.getSex().getName()));
            }

            userDtoList.add(userDTO);
        }
        return userDtoList;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Users user = userRepository.findById(userDTO.getId());

        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setFullname(userDTO.getFullname());
        user.setBirthday(userDTO.getBirthday());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setLastPayment(userDTO.getLastPay());
        user.setRoles(new Roles(userDTO.getRole().getId()));
        user.setSex(new Sex(userDTO.getSex().getId()));

        try {
            userRepository.save(user);
            LOGGER.info("Account '{}' has been updated successfully by Admin",user.getEmail());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update account '{}' by Admin: {}",user.getEmail(),e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            LOGGER.info("Account with Id '{}' has been deleted successfully by Admin",id);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete account with Id '{}' by Admin: {}",id,e.getMessage());
            return false;
        }
    }
}
