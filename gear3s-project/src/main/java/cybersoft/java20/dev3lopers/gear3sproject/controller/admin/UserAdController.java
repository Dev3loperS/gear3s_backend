package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.UserDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.security.AuthorConfirmation;
import cybersoft.java20.dev3lopers.gear3sproject.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserAdController {
    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    AuthorConfirmation confirmation;

    @GetMapping("/table")
    public ResponseEntity<?> getUserTable(HttpServletRequest req){
        BasicResponse basicResponse = new BasicResponse();

        BasicResponse response = confirmation.checkRole(req);
        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        List<UserDTO> userList = userServiceImp.readUser(true,0);
        if(userList != null){
            basicResponse.setStatusCode(HttpStatus.OK.value());
            basicResponse.setDesc("Lấy danh sách Users thành công");
            basicResponse.setData(userList);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @PostMapping("/table")
    public ResponseEntity<?> deleteUser(@RequestParam int id){
        BasicResponse basicResponse = new BasicResponse();
        if(userServiceImp.deleteUser(id)){
            basicResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
            basicResponse.setDesc("Xóa User thành công");
            basicResponse.setData(true);
        } else {
            basicResponse.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
            basicResponse.setDesc("Xóa User thất bại");
            basicResponse.setData(false);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> getUserDetail(HttpServletRequest req, @RequestParam int id){
        BasicResponse basicResponse = new BasicResponse();

        BasicResponse response = confirmation.checkRole(req);
        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        List<UserDTO> userList = userServiceImp.readUser(false,id);
        if(userList != null){
            basicResponse.setStatusCode(HttpStatus.OK.value());
            basicResponse.setDesc("Lấy thông tin User thành công");
            basicResponse.setData(userList);
        }

        return new ResponseEntity<>(basicResponse,HttpStatus.OK);
    }

    @GetMapping("/add")
    public ResponseEntity<?> getAddUser(HttpServletRequest req){
        BasicResponse response = confirmation.checkRole(req);
        if(response != null){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }

        return new ResponseEntity<>("Load trang add User thành công",HttpStatus.OK);
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
