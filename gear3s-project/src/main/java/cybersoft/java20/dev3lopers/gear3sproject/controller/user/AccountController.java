package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import com.google.gson.Gson;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PasswordDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.SexServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    SexServiceImp sexServiceImp;

    @PutMapping( "/profile")
    public ResponseEntity<?> editAccountProfile(@Valid @RequestPart("user") String userDTO,
                                                    @RequestPart(name = "file",required = false) MultipartFile avatarFile){
        Gson gson = new Gson();
        if(userServiceImp.updateUserByUser(gson.fromJson(userDTO,UserDTO.class),avatarFile)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated profile of account successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update profile of account",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> editAccountPassword(@RequestParam int userId, @Valid @RequestBody PasswordDTO passwordDTO){
        if(userServiceImp.updateUserPassword(userId,passwordDTO)){
            return new ResponseEntity<>(
                    new BasicResponse("Changed password of account successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to change password of account",false),HttpStatus.BAD_REQUEST);
        }

    }
}
