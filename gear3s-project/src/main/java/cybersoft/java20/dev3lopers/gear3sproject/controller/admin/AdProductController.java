package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class AdProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProdCreateDTO product){
        if(productService.createProduct(product)){
            return new ResponseEntity<>(
                    new BasicResponse("Added new product successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add new product", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList() {
        List<ProductDTO> productList = productService.readAllProducts();
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        ProductDTO product = productService.readProductById(productId);
        if(product != null){
            return new ResponseEntity<>(new BasicResponse("Returned product detail successful",product),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateProductById(@RequestBody ProductDTO product) {
        if(productService.updateProductById(product)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated product successfully",true),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update product",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable int productId) {
        if(productService.deleteProductById(productId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted product successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete product",false),HttpStatus.BAD_REQUEST);
        }
    }
}
