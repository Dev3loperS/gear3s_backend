package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.CatePropDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProdFilterDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.ProductDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProductServiceImp;
import cybersoft.java20.dev3lopers.gear3sproject.service.CatePropServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductServiceImp productServiceImp;

    @Autowired
    CatePropServiceImp catePropServiceImp;

    @GetMapping("/detail/{productId}")
    public ResponseEntity<?> getProductDetail(@PathVariable int productId) {
        ProductDTO product = productServiceImp.readProductById(productId);
        if(product != null){
            return new ResponseEntity<>(new BasicResponse("Returned product detail successful",product),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product detail is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/image/list/{categoryId}/{productId}")
    public ResponseEntity<?> getProductImageList(@PathVariable int categoryId, @PathVariable int productId){
        List<String> imageList = productServiceImp.readAllProductImage(productId,categoryId);
        if (imageList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned image list successful", imageList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Image list is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/popular/{categoryId}")
    public ResponseEntity<?> getProductListByPopular(@PathVariable int categoryId) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdOrderByView(categoryId);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list order by popular successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list order by popular is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/latest/{categoryId}")
    public ResponseEntity<?> getProductListByLatest(@PathVariable int categoryId) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdOrderByDate(categoryId);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list order by latest successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list order by latest is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/topsales/{categoryId}")
    public ResponseEntity<?> getProductListByTopSales(@PathVariable int categoryId) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdOrderBySold(categoryId);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list order by top sales successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list order by top sales is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/price/high2low/{categoryId}")
    public ResponseEntity<?> getProductListByPriceDesc(@PathVariable int categoryId) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdOrderByPriceDesc(categoryId);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list order by price high to low successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list order by price high to low is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/price/low2high/{categoryId}")
    public ResponseEntity<?> getProductListByPriceAsc(@PathVariable int categoryId) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdOrderByPriceAsc(categoryId);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list order by price low to high successful", productList),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(new BasicResponse("Product list order by price low to high is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/search/{categoryId}/{productName}")
    public ResponseEntity<?> getProductByName(@PathVariable int categoryId,@PathVariable String productName) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdByName(categoryId,productName);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list search by name successful", productList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product list search by name is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/price/{categoryId}/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getProductByPriceRange(@PathVariable int categoryId ,@PathVariable int minPrice ,@PathVariable int maxPrice) {
        List<ProdFilterDTO> productList = productServiceImp.readAllProdByPriceRange(categoryId,minPrice,maxPrice);
        if (productList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product list by price range successful", productList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product list by price range is empty", null),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/filter/list/{categoryId}")
    public ResponseEntity<?> getFilterListByCateId(@PathVariable int categoryId){
        List<CatePropDTO> filterList = catePropServiceImp.readProdFilterListByCateId(categoryId);
        if (filterList != null) {
            return new ResponseEntity<>(new BasicResponse("Returned product filter list by category successful", filterList),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new BasicResponse("Product filter list by category is empty", null),HttpStatus.NOT_FOUND);
        }
    }
}
