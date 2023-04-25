package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.PropDescServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prop-desc")
public class AdPropDescController {
    @Autowired
    PropDescServiceImp propDescServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addPropDescription(@RequestBody PropDescCreateDTO propDesc){
        if(propDescServiceImp.createPropDesc(propDesc)){
            return new ResponseEntity<>(
                    new BasicResponse("Added property description successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add property description", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPropDescriptionList() {
        List<PropDescDTO> propDescList = propDescServiceImp.readAllPropDesc();
        if (propDescList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned property description list successful", propDescList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Property description list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list-by-cate-prop/{catePropId}")
    public ResponseEntity<?> getPropDescriptionListByCatePropId(@PathVariable int catePropId) {
        List<PropDescCreateDTO> propDescList = propDescServiceImp.readAllPropDescByCatePropId(catePropId);
        if (propDescList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned property description list by category property successful", propDescList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Property description list by category property is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{propDescId}")
    public ResponseEntity<?> getPropDescriptionDetailById(@PathVariable int propDescId) {
        PropDescDTO propDesc = propDescServiceImp.readPropDescById(propDescId);
        if(propDesc != null){
            return new ResponseEntity<>(new BasicResponse("Returned property description detail successful",propDesc),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Property description detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updatePropDescription(@RequestBody PropDescCreateDTO propDesc) {
        if(propDescServiceImp.updatePropDesc(propDesc)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated property description successfully",true),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update property description",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePropDescriptionById(@RequestParam int propDescId) {
        if(propDescServiceImp.deletePropDescById(propDescId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted property description successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete property description",false),HttpStatus.BAD_REQUEST);
        }
    }
}
