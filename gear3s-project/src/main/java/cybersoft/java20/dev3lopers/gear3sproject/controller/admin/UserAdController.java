package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Users;
import cybersoft.java20.dev3lopers.gear3sproject.payload.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserAdController {
    @Autowired
    UserServiceImp userServiceImp;

    @GetMapping("/table")
    public ResponseEntity<?> getUserTable(){
        BasicResponse basicResponse = new BasicResponse();

        List<UserDTO> userList = userServiceImp.readUser(true,null);
        if(userList != null){
            basicResponse.setStatusCode(HttpStatus.OK.value());
            basicResponse.setDesc("Lấy danh sách Users thành công");
            basicResponse.setData(userList);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestParam String email){
        BasicResponse basicResponse = new BasicResponse();

        List<UserDTO> userList = userServiceImp.readUser(false,email);
        if(userList != null){
            basicResponse.setStatusCode(HttpStatus.OK.value());
            basicResponse.setDesc("Lấy thông tin User thành công");
            basicResponse.setData(userList);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO userDTO){
        BasicResponse basicResponse = new BasicResponse();
        if(userServiceImp.createUserByAdmin(userDTO)){
            basicResponse.setStatusCode(HttpStatus.CREATED.value());
            basicResponse.setDesc("Tạo User mới thành công");
            basicResponse.setData(true);
        }else {
            basicResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            basicResponse.setDesc("Tạo User mới thất bại");
            basicResponse.setData(false);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO){
        BasicResponse basicResponse = new BasicResponse();
        if(userServiceImp.updateUser(userDTO)){
            basicResponse.setStatusCode(HttpStatus.CREATED.value());
            basicResponse.setDesc("Cập nhập User thành công");
            basicResponse.setData(true);
        }else {
            basicResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            basicResponse.setDesc("Cập nhập User thất bại");
            basicResponse.setData(false);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }
}
