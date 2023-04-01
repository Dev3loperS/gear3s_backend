package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleListModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.LoginServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/signin")
    public ResponseEntity<?> doGetSignIn(){

        return new ResponseEntity<>("Load trang user signin thành công",HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> doPostSignIn(@RequestParam String email, @RequestParam String password){
        BasicResponse basicResponse = new BasicResponse();

        String token = checkAuthentication(email,password);
        if(!"".equals(token)){
            basicResponse.setStatusCode(HttpStatus.OK.value());
            basicResponse.setDesc("Đăng nhập thành công");
            basicResponse.setData(token);
        } else {
            basicResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            basicResponse.setDesc("Đăng nhập thất bại");
            basicResponse.setData(null);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @GetMapping("/register")
    public ResponseEntity<?> doGetRegister(){

        return new ResponseEntity<>("Load trang user register thành công",HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> doPostRegister(@RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String confirmpass){
        BasicResponse basicResponse = new BasicResponse();
        if(!confirmpass.equals(password)){
            basicResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            basicResponse.setDesc("Xác nhận mật khẩu thất bại");
            basicResponse.setData(null);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setPassword(password);

            if(userServiceImp.createUserByUser(userDTO)){
                basicResponse.setStatusCode(HttpStatus.CREATED.value());
                basicResponse.setDesc("Đăng ký người dùng thành công");
                basicResponse.setData(checkAuthentication(email,password));
            } else {
                basicResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
                basicResponse.setDesc("Đăng ký người dùng thất bại");
                basicResponse.setData(null);
            }
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    private String checkAuthentication(String email, String password){
        String token = "";
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(email,password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if(authentication != null){
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);

                token = jwtUtils.generateToken(authentication.getName());
            }
        } catch (Exception e){
            System.out.println("Error has occurred when authenticate account | "+e.getMessage());
        }

        return token;
    }


}
