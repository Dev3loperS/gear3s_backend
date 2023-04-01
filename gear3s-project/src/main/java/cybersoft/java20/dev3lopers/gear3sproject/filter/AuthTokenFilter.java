package cybersoft.java20.dev3lopers.gear3sproject.filter;

import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(ignoreURL(request)){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = parseJwt(request);
            if(!"".equals(jwt) && jwtUtils.verifyToken(jwt)){
                // Tạo chứng thực để có thể truy cập vào link
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken("","",new ArrayList<>());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(token);

                System.out.println("Your token: "+jwt);
            } else {
                System.out.println("Token not found or expired");
            }
        } catch (Exception e){
            System.out.println("Error has occurred when check token | "+e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }

        return "";
    }

    private boolean ignoreURL(HttpServletRequest request){
        String path = request.getRequestURI();
        switch (path){
            case "/admin/login/signin":
            case "/login/register":
            case "/login/signin":
                break;
            default:
                return false;
        }

        return true;
    }

}
