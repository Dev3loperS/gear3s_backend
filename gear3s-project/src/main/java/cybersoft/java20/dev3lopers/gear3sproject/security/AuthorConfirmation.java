package cybersoft.java20.dev3lopers.gear3sproject.security;

import cybersoft.java20.dev3lopers.gear3sproject.model.RoleListModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.LoginServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorConfirmation {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    LoginServiceImp loginServiceImp;

    // Kiểm tra Role
    public BasicResponse checkRole(HttpServletRequest request) {
        BasicResponse basicResponse = new BasicResponse();
        String token = getToken(request);
        if(!"".equals(token)){
            String email = jwtUtils.getSubjectFromToken(token);
            if (RoleListModel.ADMIN.getValue() != loginServiceImp.getRoleIdByEmail(email)) {
                basicResponse.setStatusCode(HttpStatus.FORBIDDEN.value());
                basicResponse.setDesc("Không có quyền admin");
                basicResponse.setData(false);

                return basicResponse;
            }
        }

        return null;
    }

    private String getToken(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }

        return "";
    }
}
