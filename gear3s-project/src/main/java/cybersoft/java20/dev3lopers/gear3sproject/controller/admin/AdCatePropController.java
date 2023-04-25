package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.CatePropServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cate-prop")
public class AdCatePropController {
    @Autowired
    CatePropServiceImp catePropServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addCateProperty(@RequestBody CatePropCreateDTO cateProperty){
        if(catePropServiceImp.createCateProp(cateProperty)){
            return new ResponseEntity<>(
                    new BasicResponse("Added category property successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add category property", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCatePropertyList() {
        List<CatePropDTO> catePropList = catePropServiceImp.readAllCateProp();
        if (catePropList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned category property list successful", catePropList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Category property list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{catePropertyId}")
    public ResponseEntity<?> getCatePropertyDetailById(@PathVariable int catePropertyId) {
        CatePropDTO cateProp = catePropServiceImp.readCatePropById(catePropertyId);
        if(cateProp != null){
            return new ResponseEntity<>(new BasicResponse("Returned category property detail successful",cateProp),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Category property detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateCateProperty(@RequestBody CatePropCreateDTO cateProperty) {
        if(catePropServiceImp.updateCateProp(cateProperty)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated category property successfully",true),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update category property",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCatePropertyById(@RequestParam int catePropertyId) {
        if(catePropServiceImp.deleteCatePropById(catePropertyId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted category property successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete category property",false),HttpStatus.BAD_REQUEST);
        }
    }
}
