package cybersoft.java20.dev3lopers.gear3sproject.exception;

import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    // Sử dụng bắt Valid Exception
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BasicResponse handleBindException(BindException e){
        // Trả về message của lỗi đầu tiên bắt gặp
        if(e.getBindingResult().hasErrors()){
            LOGGER.error("Validation error: {}",e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        }
        return new BasicResponse("Invalid email or password entered",false);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BasicResponse handleUnwantedException(Exception e) {
        LOGGER.error("System error: {}",e.getMessage());

        return new BasicResponse("Unknown error",false);
    }

}
