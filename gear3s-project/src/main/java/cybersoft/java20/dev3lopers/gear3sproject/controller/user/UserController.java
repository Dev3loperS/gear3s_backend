package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import com.google.gson.Gson;
import cybersoft.java20.dev3lopers.gear3sproject.dto.MyCardDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PasswordDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.SexServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserCardServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    SexServiceImp sexServiceImp;

    @Autowired
    UserCardServiceImp userCardServiceImp;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userId){
        UserDTO user = userServiceImp.readUserByIdByUser(userId);
        if(user != null){
            return new ResponseEntity<>(new BasicResponse("Returned user info successful", user),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("User info is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping( "/profile")
    public ResponseEntity<?> editUserProfile(@Valid @RequestPart("user") String userDTO,
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

    @PutMapping("/password/{userId}")
    public ResponseEntity<?> editUserPassword(@PathVariable int userId, @Valid @RequestBody PasswordDTO passwordDTO){
        if(userServiceImp.updateUserPassword(userId,passwordDTO)){
            return new ResponseEntity<>(
                    new BasicResponse("Changed password of account successfully",true), HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to change password of account",false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/payment/{userId}")
    public ResponseEntity<?> getUserCardList(@PathVariable int userId){
        List<MyCardDTO> myCardList = userCardServiceImp.readUserCardByUserId(userId);
        if(myCardList != null && myCardList.size() > 0){
            return new ResponseEntity<>(new BasicResponse("Returned my card list successful", myCardList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("My card list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/payment/{userId}")
    public ResponseEntity<?> addUserCard(@PathVariable int userId, @Valid @RequestBody MyCardDTO myCard){
        if(userCardServiceImp.createUserCard(userId,myCard)){
            return new ResponseEntity<>(new BasicResponse("Added new user card successful",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Failed to add new user card", false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/payment/{userId}/{myCardId}")
    public ResponseEntity<?> deleteUserCard(@PathVariable int userId, @PathVariable int myCardId){
        if(userCardServiceImp.deleteUserCard(userId,myCardId)){
            return new ResponseEntity<>(new BasicResponse("Deleted user card successful",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Failed to delete user card", false),HttpStatus.BAD_REQUEST);
        }
    }
}
