package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.payload.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.security.AuthorConfirmation;
import cybersoft.java20.dev3lopers.gear3sproject.service.LoginServiceImp;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/login")
public class LoginAdController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    AuthorConfirmation confirmation;

    @GetMapping("/signin")
    public ResponseEntity<?> doGetSignIn(){

        return new ResponseEntity<>("Load trang admin signin thành công",HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> doPostSignIn(HttpServletRequest req, @RequestParam String email, @RequestParam String password){
        BasicResponse basicResponse = new BasicResponse();
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(email,password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if(authentication != null){
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);

                BasicResponse response = confirmation.checkRole(req);
                if(response != null){
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }
                String userName = authentication.getName();
                basicResponse.setStatusCode(HttpStatus.OK.value());
                basicResponse.setDesc("Đăng nhập thành công");
                basicResponse.setData(jwtUtils.generateToken(userName));
            }
        } catch (Exception e){
            System.out.println("Error has occurred when authenticate account | "+e.getMessage());
            basicResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            basicResponse.setDesc("Đăng nhập thất bại");
            basicResponse.setData(null);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }


}
