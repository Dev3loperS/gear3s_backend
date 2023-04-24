package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdCreateDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.FileStorageServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
public class AdProductController {

    @Autowired
    ProductServiceImp productServiceImp;

    @Autowired
    FileStorageServiceImp fileStorageServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProdCreateDTO product){
        if(productServiceImp.createProduct(product)){
            return new ResponseEntity<>(
                    new BasicResponse("Added new product successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add new product", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getProductList() {
        List<ProductDTO> productList = productServiceImp.readAllProducts();
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        ProductDTO product = productServiceImp.readProductById(productId);
        if(product != null){
            return new ResponseEntity<>(new BasicResponse("Returned product detail successful",product),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        if(productServiceImp.updateProduct(product)){
            return new ResponseEntity<>(
                    new BasicResponse("Updated product successfully",true),HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to update product",false),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable int productId) {
        if(productServiceImp.deleteProductById(productId)){
            return new ResponseEntity<>(
                    new BasicResponse("Deleted product successfully",true),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to delete product",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/detail/image/add")
    public ResponseEntity<?> addProductImage(@RequestParam int categoryId, @RequestParam int productId,
                                             @RequestParam("files") MultipartFile[] prodImages){
        if(fileStorageServiceImp.uploadProdImage(categoryId,productId,prodImages)){
            return new ResponseEntity<>(
                    new BasicResponse("Add product image successfully", true), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    new BasicResponse("Failed to add product image", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/detail/image/list/{categoryId}/{productId}")
    public ResponseEntity<?> getAllProductImage(@PathVariable int categoryId, @PathVariable int productId){
        List<String> imageList = fileStorageServiceImp.readAllProdImage(categoryId,productId);
        if (imageList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product image list successful", imageList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product image list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/detail/image/delete")
    public ResponseEntity<?> deleteProductImage(@RequestParam String imagePath){
        if (fileStorageServiceImp.deleteFile(imagePath)) {
            return new ResponseEntity<>(new BasicResponse("Deleted file successful",true),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Failed to delete file", false),HttpStatus.BAD_REQUEST);
        }
    }

}
