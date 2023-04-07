package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.LoginRequest;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.JwtResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailsImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/login")
public class LoginAdController {
    @Value("${jwt.privateKey}")
    private String privateKey;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceImp userServiceImp;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if(!"".equals(authentication.getName())){
            String jwt = jwtUtils.generateToken(loginRequest.getEmail());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            AccountDetailsImp userDetails =
                    (AccountDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(!userDetails.getRole().equals(RoleModel.ADMIN.getValue())){
                return new ResponseEntity<>(
                        new BasicResponse("You're not Admin. Get out of here!",null),HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(
                    new BasicResponse("Signed in as Admin successful",jwtResponse(userDetails,jwt)),HttpStatus.OK);
        }

        return new ResponseEntity<>(
                new BasicResponse("Failed to sign in as Admin",null),HttpStatus.UNAUTHORIZED);
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
