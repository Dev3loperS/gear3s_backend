package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.RoleDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.SexDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.RoleServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.SexServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
public class UserAdController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    RoleServiceImp roleServiceImp;

    @Autowired
    SexServiceImp sexServiceImp;

    @GetMapping("/add")
    public ResponseEntity<?> getAddUser(){

        return new ResponseEntity<>(
                new BasicResponse("Return roles & sex list",getRoleAndSexList()),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) {
        if (userServiceImp.createUserByAdmin(userDTO)) {

            return new ResponseEntity<>(
                    new BasicResponse("Added new user successfully", true), HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(
                    new BasicResponse("Failed to add new user", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/table")
    public ResponseEntity<?> getUserTable(){

        return new ResponseEntity<>(new BasicResponse("Return users list",
                                        userServiceImp.readUser(true,0)),HttpStatus.OK);
    }

    @DeleteMapping("/table")
    public ResponseEntity<?> deleteUser(@RequestParam int id){
        if(userServiceImp.deleteUser(id)){

            return new ResponseEntity<>(
                    new BasicResponse("Deleted user successfully",true),HttpStatus.OK);
        } else {

            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete user",false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestParam int id){

        return new ResponseEntity<>(new BasicResponse("Return user info",
                                        userServiceImp.readUser(false,id)),HttpStatus.OK);
    }

    @GetMapping("/edit")
    public ResponseEntity<?> getEditUser(){

        return new ResponseEntity<>(
                new BasicResponse("Return roles & sex list",getRoleAndSexList()),HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO){
        if(userServiceImp.updateUser(userDTO)){

            return new ResponseEntity<>(
                    new BasicResponse("Updated user successfully",true),HttpStatus.CREATED);
        }else {

            return new ResponseEntity<>(
                    new BasicResponse("Failed to update user",false),HttpStatus.BAD_REQUEST);
        }
    }

    private List<BasicResponse> getRoleAndSexList(){
        List<BasicResponse> responseList = new ArrayList<>();

        responseList.add(new BasicResponse("Return roles list",roleServiceImp.getAllRole()));
        responseList.add(new BasicResponse("Return sex list",sexServiceImp.getAllSex()));

        return responseList;
    }



}
