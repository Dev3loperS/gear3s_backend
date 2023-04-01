package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;

public interface LoginService {
    boolean checkLogin(String email, String password);
    int getRoleIdByEmail(String email);
}
