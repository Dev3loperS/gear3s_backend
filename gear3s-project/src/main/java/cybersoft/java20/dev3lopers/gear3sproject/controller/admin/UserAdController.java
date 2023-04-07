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

    @GetMapping("/table")
    public ResponseEntity<?> getUserTable(){

        return new ResponseEntity<>(new BasicResponse("Trả danh sách Users",
                                        userServiceImp.readUser(true,0)),HttpStatus.OK);
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

        return new ResponseEntity<>(new BasicResponse("Trả thông tin User",
                                        userServiceImp.readUser(false,id)),HttpStatus.OK);

    }

    @GetMapping("/add")
    public ResponseEntity<?> getAddUser(){

        return new ResponseEntity<>(
                new BasicResponse("Load trang tạo User",getRoleAndSexList()),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO){
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
                new BasicResponse("Load trang cập nhập thông tin User",getRoleAndSexList()),HttpStatus.OK);
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

    private List<BasicResponse> getRoleAndSexList(){
        List<BasicResponse> responseList = new ArrayList<>();

        List<RoleDTO> roleList = roleServiceImp.getAllRole();
        BasicResponse roleResp = new BasicResponse();
        if (roleList != null){
            roleResp.setMessage("Lấy danh sách Role thành công");
            roleResp.setData(roleList);
        } else {
            roleResp.setMessage("Lấy danh sách Role thất bại");
            roleResp.setData(null);
        }
        responseList.add(roleResp);

        List<SexDTO> sexList = sexServiceImp.getAllSex();
        BasicResponse sexResp = new BasicResponse();
        if(sexList != null){
            sexResp.setMessage("Lấy danh sách Sex thành công");
            sexResp.setData(sexList);
        } else {
            sexResp.setMessage("Lấy danh sách Sex thất bại");
            sexResp.setData(null);
        }
        responseList.add(sexResp);

        return responseList;
    }



}
