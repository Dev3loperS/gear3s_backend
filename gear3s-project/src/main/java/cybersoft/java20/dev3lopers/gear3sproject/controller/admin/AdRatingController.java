package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdRatingDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProdRatingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class AdRatingController {

    @Autowired
    ProdRatingServiceImp prodRatingServiceImp;

    @GetMapping("/list")
    public ResponseEntity<?> getProductRatingList() {
        List<ProdRatingDTO> ratingList = prodRatingServiceImp.getAllProdRating();
        if (ratingList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product rating list successful", ratingList), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product rating list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{ratingId}")
    public ResponseEntity<?> getProductRatingDetailById(@PathVariable int ratingId) {
        ProdRatingDTO rating = prodRatingServiceImp.getProdRatingById(ratingId);
        if (rating != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product rating detail successful", rating),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product rating detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{ratingId}")
    public ResponseEntity<?> deleteProductRatingById(@PathVariable int ratingId) {
        if(prodRatingServiceImp.deleteProdRating(ratingId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted product rating successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete product rating",false),HttpStatus.BAD_REQUEST);
        }
    }
}
