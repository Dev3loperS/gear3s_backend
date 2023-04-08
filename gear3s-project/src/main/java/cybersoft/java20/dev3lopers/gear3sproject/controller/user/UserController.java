package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

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
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    SexServiceImp sexServiceImp;

    @PutMapping("/profile")
    public ResponseEntity<?> editUserProfile(@Valid @RequestBody UserDTO userDTO){
        if(userServiceImp.updateUserByUser(userDTO)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated profile of user successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update profile of user",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> editUserAvatar(@RequestParam int userId,@RequestParam MultipartFile avatarFile){
        if(userServiceImp.updateAvatar(userId,avatarFile)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated avatar of user successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to avatar profile of user",false),HttpStatus.BAD_REQUEST);
        }

    }
}
