package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean checkLogin(String email, String password);
    boolean checkEmailExistence(String email);
    boolean createUserByUser(UserDTO userDTO);
    boolean createUserByAdmin(UserDTO userDTO);
    List<UserDTO> readUser(boolean getAllUser, int id);
    boolean updateUser(UserDTO userDTO);
    boolean deleteUser(int id);
}
