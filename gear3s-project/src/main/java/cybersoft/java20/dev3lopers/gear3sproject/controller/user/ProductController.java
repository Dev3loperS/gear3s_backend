package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.FilterRequest;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProdRatingServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProductServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.CatePropServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductServiceImp productServiceImp;

    @Autowired
    CatePropServiceImp catePropServiceImp;

    @Autowired
    ProdRatingServiceImp prodRatingServiceImp;

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        ProductDTO product = productServiceImp.readProductById(productId);
        if(product != null){
            return new ResponseEntity<>(new BasicResponse("Returned product detail successful",product),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/property/{productId}")
    public ResponseEntity<?> getProductPropertyDesc(@PathVariable int productId) {
        List<ProdPropDescDTO> propDescList = productServiceImp.readProdPropDescById(productId);
        if(propDescList != null){
            return new ResponseEntity<>(new BasicResponse("Returned product property description successful",propDescList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product property description is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{categoryId}/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable int categoryId,@PathVariable String productName) {
        List<ShortProdDTO> productList = productServiceImp.readAllProdByName(categoryId,productName);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list search by name successful", productList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product list search by name is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/list-property/{categoryId}")
    public ResponseEntity<?> getFilterListByCateId(@PathVariable int categoryId){
        List<CatePropFilterDTO> filterList = catePropServiceImp.readProdFilterListByCateId(categoryId);
        if (filterList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product filter list by category successful", filterList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product filter list by category is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<?> getProdListAfterFilter(@RequestBody FilterRequest request){
        List<ShortProdDTO> productList = productServiceImp.readAllProdAfterFilter(request);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list after filter successful", productList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product list after filter is empty", null),HttpStatus.NOT_FOUND);
        }
    }
    /*
    * {
          "categoryId": 1,
          "maxPrice": 3000000,
          "minPrice": 500000,
          "propDescList":
          [
            {
              "descId": [3,1],
              "propertyId": 31
            },
            {
              "descId": [4],
              "propertyId": 7
            },
            {
              "descId": [7],
              "propertyId": 8
            },
            {
              "descId": [0],
              "propertyId": 9
            },
            {
              "descId": [11],
              "propertyId": 6
            }
           ],
          "sortType": "topSales"
      }
    */

    @PutMapping("/detail/rating/add")
    public ResponseEntity<?> addRatingForProduct(@RequestParam int userId,
                                                 @RequestParam int productId,
                                                 @RequestParam byte star,
                                                 @RequestParam String comment){
        if(prodRatingServiceImp.createProdRatingByUser(userId,productId,star,comment)){
            return new ResponseEntity<>(
                    new BasicResponse("Add rating for product successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add rating for product",false),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/detail/rating/list/{productId}")
    public ResponseEntity<?> getAllProductRatingByProdId(@PathVariable int productId){
        List<ProdRatingDTO> ratingList = prodRatingServiceImp.getAllRatingOfProd(productId);
        if (ratingList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product rating list successful", ratingList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product rating list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/detail/rating/delete/{ratingId}")
    public ResponseEntity<?> deleteProductRatingById(@PathVariable int ratingId) {
        if(prodRatingServiceImp.deleteProdRating(ratingId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted product rating successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete product rating",false),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/rating/start-average/{productId}")
    public ResponseEntity<?> getAverageRatingStarByProdId(@PathVariable int productId){
        float averageStar = prodRatingServiceImp.getAverageStarOfProd(productId);
        if (averageStar >= 0) {
            return new ResponseEntity<>(new BasicResponse("Returned average rating star successful", averageStar),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Average rating start is empty", null),HttpStatus.NOT_FOUND);
        }
    }
}
