package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Roles;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleListModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.UserRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class LoginServiceImp implements LoginService {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean checkLogin(String email, String password) {
        return userRepository.countByEmailAndPassword(email,password) > 0;
    }

    @Override
    public int getRoleIdByEmail(String email) {

        return userRepository.getRoleByEmail(email);
    }

}
