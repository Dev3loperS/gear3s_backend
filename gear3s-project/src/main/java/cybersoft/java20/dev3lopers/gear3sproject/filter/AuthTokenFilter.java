package cybersoft.java20.dev3lopers.gear3sproject.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import cybersoft.java20.dev3lopers.gear3sproject.model.RoleModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.AccountDetailsImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AccountDetailServiceImp accountDetailServiceImp;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        //System.out.println(request.getServletPath());
        try {
            String jwt = parseJwt(request);
            if(jwt == null && request.getCookies() != null) {
                Cookie[] cookies = request.getCookies();
                for(int i = cookies.length - 1; i >= 0; i--) {
                    if ("jwt".equals(cookies[i].getName()) && cookies[i].getValue() != null) {
                        jwt = cookies[i].getValue();
                        break;
                    }
                }
            }
            if(jwt != null && jwtUtils.verifyToken(jwt)){
                // Tạo chứng thực để có thể truy cập vào link
                String username = jwtUtils.getSubjectFromToken(jwt);
                UserDetails userDetails = accountDetailServiceImp.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                //authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // Kiểm tra phân quyền (có phải admin hay không) khi vào các trang có /api/admin/**
                AccountDetailsImp account =
                        (AccountDetailsImp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if("/api/admin".equals(request.getRequestURI().substring(0,10))){
                    if(!account.getRole().equals(RoleModel.ADMIN.getValue())){
                        LOGGER.error("Account '{}' does not have Admin permission",account.getEmail());
                        response.setHeader("Content-Type", "application/json");
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        String responseToSend = new ObjectMapper().writeValueAsString(
                                new BasicResponse("You're not Admin. Get out of here!",null));
                        response.getOutputStream().write(responseToSend.getBytes());
                        return;
                    }
                }
            }
        } catch (Exception e){
            LOGGER.error("Error has occurred in 'doFilterInternal' : {}",e.getMessage());
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

}
