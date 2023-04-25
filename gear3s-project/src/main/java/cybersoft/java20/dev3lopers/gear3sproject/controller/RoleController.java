package cybersoft.java20.dev3lopers.gear3sproject.controller;

import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.RoleServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleServiceImp roleServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> getRoleList(){
        return new ResponseEntity<>(new BasicResponse("Returned role list",roleServiceImp.readAllRole()),HttpStatus.OK);
    }
}
