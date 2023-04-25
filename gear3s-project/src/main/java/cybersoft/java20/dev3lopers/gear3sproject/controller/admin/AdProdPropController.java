package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdPropCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdPropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.PropDescDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProdPropServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/prod-prop")
public class AdProdPropController {
    @Autowired
    ProdPropServiceImp prodPropServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addProdProperty(@RequestBody ProdPropCreateDTO prodProp){
        if(prodPropServiceImp.createProdProp(prodProp)){
            return new ResponseEntity<>(
                    new BasicResponse("Added product property successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add product property", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProdPropertyList() {
        List<ProdPropDTO> prodPropList = prodPropServiceImp.readAllProdProp();
        if (prodPropList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product property list successful", prodPropList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product property list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{prodPropId}")
    public ResponseEntity<?> getProdPropertyDetailById(@PathVariable int prodPropId) {
        ProdPropDTO prodProp = prodPropServiceImp.readProdPropById(prodPropId);
        if(prodProp != null){
            return new ResponseEntity<>(new BasicResponse("Returned product property detail successful",prodProp),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product property detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateProdProperty(@RequestBody ProdPropCreateDTO prodProp) {
        if(prodPropServiceImp.updateProdProp(prodProp)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated product property successfully",true),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update product property",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProdPropertyById(@RequestParam int prodPropId) {
        if(prodPropServiceImp.deleteProdPropById(prodPropId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted product property successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete product property",false),HttpStatus.BAD_REQUEST);
        }
    }
}
