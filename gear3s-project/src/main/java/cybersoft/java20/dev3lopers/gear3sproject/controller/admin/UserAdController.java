package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserAdController {
    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/table")
    public ResponseEntity<?> getUserTable(){
        List<UserDTO> userList = userServiceImp.readUser(true,0);
        if(userList != null){

            return new ResponseEntity<>(
                    new BasicResponse("Lấy danh sách Users thành công",userList),HttpStatus.OK);
        } else {

            return new ResponseEntity<>(
                    new BasicResponse("Lấy danh sách Users thất bại",null),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/table")
    public ResponseEntity<?> deleteUser(@RequestParam int id){
        if(userServiceImp.deleteUser(id)){

            return new ResponseEntity<>(
                    new BasicResponse("Xóa User thành công",true),HttpStatus.OK);
        } else {

            return new ResponseEntity<>(
                    new BasicResponse("Xóa User thất bại",false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestParam int id){
        List<UserDTO> userList = userServiceImp.readUser(false,id);
        if(userList != null){

            return new ResponseEntity<>(
                    new BasicResponse("Lấy thông tin User thành công",userList),HttpStatus.OK);
        } else {

            return new ResponseEntity<>(
                    new BasicResponse("Lấy thông tin User thất bại",null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/add")
    public ResponseEntity<?> getAddUser(){

        return new ResponseEntity<>(
                new BasicResponse("Load trang tạo User thành công",null),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        if(userServiceImp.createUserByAdmin(userDTO)){

            return new ResponseEntity<>(
                    new BasicResponse("Tạo User mới thành công",true),HttpStatus.CREATED);
        }else {

            return new ResponseEntity<>(
                    new BasicResponse("Tạo User mới thất bại",false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/edit")
    public ResponseEntity<?> getEditUser(){

        return new ResponseEntity<>(
                new BasicResponse("Load trang cập nhập thông tin User thành công",null),HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO){
        if(userServiceImp.updateUser(userDTO)){

            return new ResponseEntity<>(
                    new BasicResponse("Cập nhập thông tin User thành công",true),HttpStatus.CREATED);
        }else {

            return new ResponseEntity<>(
                    new BasicResponse("Cập nhập thông tin User thất bại",false),HttpStatus.BAD_REQUEST);
        }
    }



}
