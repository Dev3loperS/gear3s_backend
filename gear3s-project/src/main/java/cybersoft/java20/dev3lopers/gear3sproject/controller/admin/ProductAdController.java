package cybersoft.java20.dev3lopers.gear3sproject.controller.admin;

import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductAdController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        BasicResponse basicResponse = new BasicResponse();
        List<ProductDTO> productDTO = productService.getAllProducts();
        if (!productDTO.isEmpty())
        {
            basicResponse.setMessage("true");
            basicResponse.setData(productDTO);
        }else
        {
            basicResponse.setMessage("false");
            basicResponse.setData(productDTO);
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam int id) {


        ProductDTO productDTO = productService.findById(id);
        BasicResponse basicResponse = new BasicResponse();
        if (productDTO != null) {
            basicResponse.setData(productDTO);
            basicResponse.setMessage("true");

        } else {
            basicResponse.setMessage("false");
            basicResponse.setData(productDTO);

        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name) {
        ProductDTO productDTO = productService.findByName(name);
        BasicResponse basicResponse = new BasicResponse();
        if (productDTO != null) {
            basicResponse.setData(productDTO);
            basicResponse.setMessage("true");

        } else {
            basicResponse.setMessage("false");
            basicResponse.setData(productDTO);

        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/findByOriginPrice")
    public ResponseEntity<?> findByOriginPrice(@RequestParam int price) {

        List<ProductDTO> productDTOS = productService.findByPriceOrigin(price);
        BasicResponse basicResponse = new BasicResponse();
        if (!productDTOS.isEmpty())
        {
            basicResponse.setData(productDTOS);
            basicResponse.setMessage("true");
        }else
        {
            basicResponse.setData(productDTOS);
            basicResponse.setMessage("false ");
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/findByDiscountPrice")
    public ResponseEntity<?> findByDiscountPrice(@RequestParam int price) {

        BasicResponse basicResponse = new BasicResponse();

        List<ProductDTO> productDTOS = productService.findByPriceDiscount(price);
        if (!productDTOS.isEmpty())
        {
            basicResponse.setData(productDTOS);
            basicResponse.setMessage("true");
        }else
        {
            basicResponse.setData(productDTOS);
            basicResponse.setMessage("false ");
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam int id) {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = productService.deleteProductById(id);
        if (isSuccess) {
            basicResponse.setMessage("Delete succesfully product id " + id);
            basicResponse.setData(isSuccess);

        } else {
            basicResponse.setMessage("Delete unsuccesfully product id " + id);
            basicResponse.setData(isSuccess);

        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);

    }
    @PostMapping("/updateById/{id}")
    public ResponseEntity<?> updateById(@PathVariable ("id") int id,@RequestBody ProductDTO productDTO) {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = productService.updateProductById(id,productDTO);
        if (isSuccess) {
            basicResponse.setMessage("Update succesfully product id " + id);
            basicResponse.setData(isSuccess);

        } else {
            basicResponse.setMessage("Update unsuccesfully product id " + id);
            basicResponse.setData(isSuccess);

        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);

    }
}
