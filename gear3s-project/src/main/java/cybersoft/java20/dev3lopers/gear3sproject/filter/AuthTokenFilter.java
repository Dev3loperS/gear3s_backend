package cybersoft.java20.dev3lopers.gear3sproject.filter;

import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailsImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountDetailServiceImp accountDetailServiceImp;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(ignoreURL(request)){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.verifyToken(jwt)){
                // Tạo chứng thực để có thể truy cập vào link
                /*UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken("","",new ArrayList<>());*/
                String username = jwtUtils.getSubjectFromToken(jwt);
                UserDetails userDetails = accountDetailServiceImp.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Kiểm tra phân quyền
                AccountDetailsImp account =
                        (AccountDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if("/admin".equals(request.getRequestURI().substring(0,6))){
                    //System.out.println(request.getRequestURI().substring(0,6));
                    if(!account.getRole().equals(RoleModel.ADMIN.getValue())){
                        // Đưa về trang 403.html
                        return;
                    }
                }
                System.out.println("Your token: "+jwt);
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

        return null;
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
