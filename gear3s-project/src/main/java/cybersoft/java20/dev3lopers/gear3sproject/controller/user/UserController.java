package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import com.google.gson.Gson;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.SexServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PutMapping(value = "/profile",consumes = {MediaType.APPLICATION_JSON_VALUE,
                                                    MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> editUserProfile(@Valid @RequestPart() String userDTO,
                                                        @RequestPart(required = false) MultipartFile avatarFile){
        Gson gson = new Gson();

        if(userServiceImp.updateUserByUser(gson.fromJson(userDTO,UserDTO.class),avatarFile)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated profile of user successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update profile of user",false),HttpStatus.BAD_REQUEST);
        }
    }
}
