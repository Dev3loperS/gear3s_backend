package cybersoft.java20.dev3lopers.gear3sproject.exceptionhandle;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class GlobalExceptionHandler {
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBindException(BindException e){
        // Trả về message của lỗi đầu tiên bắt gặp
        String errorMess = "Request không hợp lệ";
        if(e.getBindingResult().hasErrors()){
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return errorMess;
    }
}
