package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.AccountDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PasswordDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    boolean checkLogin(String email, String password);
    boolean checkEmailExistence(String email);
    boolean createUser(AccountDTO accountDTO,boolean byAdmin);
    List<UserDTO> readAllUser();
    UserDTO readUserById(int id);
    boolean updateUserByUser(UserDTO userDTO, MultipartFile avatarFile);
    //boolean updateUserByUser(UserDTO userDTO);
    //boolean updateAvatar(int userId, MultipartFile avatarFile);
    boolean updateUserRoleByAdmin(int userId, int roleId);
    boolean deleteUser(int id);
    boolean updateUserPassword(int userId, PasswordDTO passwordDTO);


}
