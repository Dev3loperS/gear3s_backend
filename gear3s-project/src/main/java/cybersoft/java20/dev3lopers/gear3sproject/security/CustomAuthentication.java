package cybersoft.java20.dev3lopers.gear3sproject.security;

import cybersoft.java20.dev3lopers.gear3sproject.service.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthentication implements AuthenticationProvider {
    @Autowired
    LoginServiceImp loginServiceImp;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(loginServiceImp.checkLogin(email,password)){
            return new UsernamePasswordAuthenticationToken(email,password,new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
