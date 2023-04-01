package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.SexDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Sex;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleListModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean createUserByAdmin(UserDTO userDTO) {
        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setBirthday(userDTO.getBirthday());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setLastPayment(0);

        Roles roles = new Roles();
        roles.setId(userDTO.getRole().getId());
        user.setRoles(roles);

        Sex sex = new Sex();
        sex.setId(userDTO.getSex().getId());
        user.setSex(sex);

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e){
            System.out.println("Error has occurred when create user by admin | "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean createUserByUser(UserDTO userDTO) {
        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setLastPayment(0);

        Roles roles = new Roles();
        roles.setId(RoleListModel.USER.getValue());
        user.setRoles(roles);

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e){
            System.out.println("Error has occurred when create user by user | "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<UserDTO> readUser(boolean getAllUser,String email) {
        List<UserDTO> userDtoList = new ArrayList<>();
        List<Users> usersList = new ArrayList<>();
        if(getAllUser){
            usersList = userRepository.findAll();
        } else {
            Users users = userRepository.findByEmail(email);
            usersList.add(users);
        }

        for (Users user: usersList) {
            UserDTO userDTO = new UserDTO();

            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setFullname(user.getFullname());
            userDTO.setBirthday(user.getBirthday());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            userDTO.setAvatar(user.getAvatar());
            userDTO.setLastPay(user.getLastPayment());

            if(user.getRoles() != null){
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setName(user.getRoles().getName());
                userDTO.setRole(roleDTO);
            }
            if(user.getSex() != null){
                SexDTO sexDTO = new SexDTO();
                sexDTO.setName(user.getSex().getName());
                userDTO.setSex(sexDTO);
            }

            userDtoList.add(userDTO);
        }
        return userDtoList;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        Users user = userRepository.findById(userDTO.getId());

        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setBirthday(userDTO.getBirthday());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setAvatar(userDTO.getAvatar());
        user.setLastPayment(userDTO.getLastPay());

        Roles roles = new Roles();
        roles.setId(userDTO.getRole().getId());
        user.setRoles(roles);

        Sex sex = new Sex();
        sex.setId(userDTO.getSex().getId());
        user.setSex(sex);

        try {
            userRepository.save(user);
            return true;
        } catch (Exception e){
            System.out.println("Error has occurred when update user by admin | "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }
}
