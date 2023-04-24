package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.AccountDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.AdUserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.RoleServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.SexServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class AdUserController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    RoleServiceImp roleServiceImp;

    @Autowired
    SexServiceImp sexServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody AccountDTO accountDTO) {
        if (userServiceImp.createUser(accountDTO,true)) {
            return new ResponseEntity<>(
                    new BasicResponse("Added new user successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add new user", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserList(){
        List<AdUserDTO> userList = userServiceImp.readAllUser();
        if(userList != null){
            return new ResponseEntity<>(new BasicResponse("Returned user list successful", userList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("User list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<?> getUserDetail(@PathVariable int userId){
        AdUserDTO user = userServiceImp.readUserByIdByAdmin(userId);
        if(user != null){
            return new ResponseEntity<>(new BasicResponse("Returned user info successful", user),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("User info is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/role/{userId}/{roleId}")
    public ResponseEntity<?> editUserRoleById(@PathVariable int userId, @PathVariable int roleId){
        if(userServiceImp.updateUserRoleByAdmin(userId,roleId)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated role of user successfully",true),HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update role of user",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable int userId){
        if(userServiceImp.deleteUser(userId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted user successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete user",false),HttpStatus.BAD_REQUEST);
        }
    }


}
