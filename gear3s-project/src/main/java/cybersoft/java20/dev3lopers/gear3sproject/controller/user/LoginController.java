package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.LoginRequest;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.RegisterRequest;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.JwtResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailsImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.LoginServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Value("${jwt.privateKey}")
    private String privateKey;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    UserServiceImp userServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest){
        try {
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if(authentication != null){
                String jwt = jwtUtils.generateToken(loginRequest.getEmail());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                AccountDetailsImp userDetails =
                        (AccountDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                return new ResponseEntity<>(
                        new BasicResponse("Đăng nhập thành công",jwtResponse(userDetails,jwt)),HttpStatus.OK);
            }
        } catch (Exception e){
            System.out.println("Error has occurred when sign in for user | "+e.getMessage());
        }

        return new ResponseEntity<>(
                new BasicResponse("Đăng nhập thất bại",null),HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest register){
        if(!register.getConfirmpass().equals(register.getPassword())){
            return new ResponseEntity<>(
                    new BasicResponse("Xác nhận mật khẩu không khớp",null),HttpStatus.BAD_REQUEST);
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(register.getEmail());
            userDTO.setPassword(register.getPassword());

            if(userServiceImp.createUserByUser(userDTO)){
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(register.getEmail(),register.getPassword());
                Authentication authentication = authenticationManager.authenticate(authenticationToken);

                String jwt = jwtUtils.generateToken(register.getEmail());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                AccountDetailsImp userDetails =
                        (AccountDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                return new ResponseEntity<>(
                        new BasicResponse("Tạo User mới thành công",jwtResponse(userDetails,jwt)),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(
                        new BasicResponse("Tạo User mới thất bại",null),HttpStatus.BAD_REQUEST);
            }
        }
    }

    private JwtResponse jwtResponse(AccountDetailsImp account,String jwt){
        SecretKey key = Keys.hmacShaKeyFor(privateKey.getBytes());

        return new JwtResponse(
                account.getId(),
                account.getEmail(),
                account.getFullname(),
                account.getAvatar(),
                account.getRole(),
                jwt,
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody()
                        .getIssuedAt().getTime()/1000,
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody()
                        .getExpiration().getTime()/1000
        );
    }

}
