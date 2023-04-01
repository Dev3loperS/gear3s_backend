package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;

import java.util.List;

public interface UserService {
    boolean createUserByUser(UserDTO userDTO);
    boolean createUserByAdmin(UserDTO userDTO);
    List<UserDTO> readUser(boolean getAllUser, String email);
    boolean updateUser(UserDTO userDTO);
    boolean deleteUser(String email);
}
