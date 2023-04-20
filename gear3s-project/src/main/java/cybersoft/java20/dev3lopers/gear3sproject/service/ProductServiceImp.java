package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Category;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import cybersoft.java20.dev3lopers.gear3sproject.model.CategoryModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisKeyModel;
import cybersoft.java20.dev3lopers.gear3sproject.repository.ProductRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImp implements ProductService {
    @Value("${uploads.path}")
    private String imagePath;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);

    private String getImagePath (int categoryId){
        if (categoryId == CategoryModel.KEYBOARD.getValue()){
            return imagePath+ImagesModel.KEYBOARD.getValue();
        } else if(categoryId == CategoryModel.MOUSE.getValue()){
            return imagePath+ImagesModel.MOUSE.getValue();
        } else if(categoryId == CategoryModel.LAPTOP.getValue()){
            return imagePath+ImagesModel.LAPTOP.getValue();
        } else if(categoryId == CategoryModel.SPEAKER.getValue()){
            return imagePath+ImagesModel.SPEAKER.getValue();
        } else if(categoryId == CategoryModel.HEADSET.getValue()){
            return imagePath+ImagesModel.HEADSET.getValue();
        } else {
            return "";
        }
    }

    private void clearRedisData(){
        redisTemplate.delete(RedisKeyModel.PRODUCTS.getValue());
        redisTemplate.delete(RedisKeyModel.PROD_POPULAR.getValue());
        redisTemplate.delete(RedisKeyModel.PROD_LATEST.getValue());
        redisTemplate.delete(RedisKeyModel.PROD_TOPSALES.getValue());
        redisTemplate.delete(RedisKeyModel.PROD_PRICE_H2L.getValue());
        redisTemplate.delete(RedisKeyModel.PROD_PRICE_L2H.getValue());
    }

    @Override
    public boolean createProduct(ProductDTO productDTO) {
        String localDate = LocalDate.now().toString();

        try {
            Product product = new Product() ;
            product.setName(productDTO.getName());
            product.setOriginPrice(productDTO.getPrice_origin());
            product.setDiscountPrice(productDTO.getPrice_discount());
            product.setInventory(productDTO.getInventory());
            product.setSoldQty(0);
            product.setView_qty(0);
            product.setDescription(productDTO.getDescription());
            product.setCreate_date(new SimpleDateFormat("yyyy-MM-dd").parse(localDate));
            product.setCategory(new Category(productDTO.getCategoryId()));

            productRepository.save(product);
            clearRedisData();
            LOGGER.info("Product with Id '{}' has been created successfully",product.getId());
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to create product : {}",e.getMessage());
            return false;
        }
    }

    private List<ProdFilterDTO> getProductFilterDtoList(String type, List<Product> prodList){
        List<ProdFilterDTO> prodDtoList = new ArrayList<>();
        try {
            for (Product product : prodList){
                ProdFilterDTO productDTO = new ProdFilterDTO();

                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setOriginPrice(product.getOriginPrice());
                productDTO.setDiscountPrice(product.getDiscountPrice());
                productDTO.setSoldQty(product.getSoldQty());
                if(product.getCategory() != null){
                    productDTO.setCategoryId(product.getCategory().getId());
                    productDTO.setImage(getImagePath(product.getCategory().getId())+product.getId()+"-1.png");
                }
                prodDtoList.add(productDTO);
            }
            LOGGER.info("Read product list order by '{}' successfully",type);
            return prodDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read product list order by '{}' : {}",type,e.getMessage());
            return null;
        }
    }

    private List<ProdFilterDTO> getDataFromRedis(String filterName, List<Product> prodList){
        Gson gson = new Gson();
        try {
            String data = (String) redisTemplate.opsForValue().get(filterName);
            if(data == null){
                List<ProdFilterDTO> prodFilterDtoList = getProductFilterDtoList(filterName,prodList);
                if(prodFilterDtoList != null){
                    redisTemplate.opsForValue().set(filterName,gson.toJson(prodFilterDtoList));
                    redisTemplate.expire(filterName,30, TimeUnit.MINUTES);
                }
                return prodFilterDtoList;
            } else {
                return gson.fromJson(data, new TypeToken<List<ProductDTO>>(){}.getType());
            }
        } catch (Exception e){
            LOGGER.error("Failed to read product list order by '{}' in Redis : {}",filterName,e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProdFilterDTO> readAllProdOrderByView(int categoryId) {
        List<Product> prodList;
        List<ProdFilterDTO> prodFilterDtoList;

        if(categoryId == 0) {
            prodList = productRepository.findAllProdOrderByViewQty();
            prodFilterDtoList = getDataFromRedis(RedisKeyModel.PROD_POPULAR.getValue(),prodList);
        }
        else {
            prodList = productRepository.findAllProdByCateIdOrderByViewQty(categoryId);
            prodFilterDtoList = getProductFilterDtoList(RedisKeyModel.PROD_POPULAR.getValue(),prodList);
        }

        return prodFilterDtoList;
    }

    @Override
    public List<ProdFilterDTO> readAllProdOrderByDate(int categoryId) {
        List<Product> prodList;
        List<ProdFilterDTO> prodFilterDtoList;

        if(categoryId == 0) {
            prodList = productRepository.findAllProdOrderByCreateDate();
            prodFilterDtoList = getDataFromRedis(RedisKeyModel.PROD_LATEST.getValue(),prodList);
        }
        else {
            prodList = productRepository.findAllProdByCateIdOrderByCreateDate(categoryId);
            prodFilterDtoList = getProductFilterDtoList(RedisKeyModel.PROD_LATEST.getValue(),prodList);
        }

        return prodFilterDtoList;
    }

    @Override
    public List<ProdFilterDTO> readAllProdOrderBySold(int categoryId) {
        List<Product> prodList;
        List<ProdFilterDTO> prodFilterDtoList;

        if(categoryId == 0) {
            prodList = productRepository.findAllProdOrderBySoldQty();
            prodFilterDtoList = getDataFromRedis(RedisKeyModel.PROD_TOPSALES.getValue(),prodList);
        }
        else {
            prodList = productRepository.findAllProdByCateIdOrderBySoldQty(categoryId);
            prodFilterDtoList = getProductFilterDtoList(RedisKeyModel.PROD_TOPSALES.getValue(),prodList);
        }

        return prodFilterDtoList;
    }

    @Override
    public List<ProdFilterDTO> readAllProdOrderByPriceDesc(int categoryId) {
        List<Product> prodList;
        List<ProdFilterDTO> prodFilterDtoList;

        if(categoryId == 0) {
            prodList = productRepository.findAllProdOrderByPriceDesc();
            prodFilterDtoList = getDataFromRedis(RedisKeyModel.PROD_PRICE_H2L.getValue(),prodList);
        }
        else {
            prodList = productRepository.findAllProdByCateIdOrderByPriceDesc(categoryId);
            prodFilterDtoList = getProductFilterDtoList(RedisKeyModel.PROD_PRICE_H2L.getValue(),prodList);
        }

        return prodFilterDtoList;
    }

    @Override
    public List<ProdFilterDTO> readAllProdOrderByPriceAsc(int categoryId) {
        List<Product> prodList;
        List<ProdFilterDTO> prodFilterDtoList;

        if(categoryId == 0) {
            prodList = productRepository.findAllProdOrderByPriceAsc();
            prodFilterDtoList = getDataFromRedis(RedisKeyModel.PROD_PRICE_L2H.getValue(),prodList);
        }
        else {
            prodList = productRepository.findAllProdByCateIdOrderByPriceAsc(categoryId);
            prodFilterDtoList = getProductFilterDtoList(RedisKeyModel.PROD_PRICE_L2H.getValue(),prodList);
        }

        return prodFilterDtoList;
    }

    @Override
    public List<ProdFilterDTO> readAllProdByPriceRange(int categoryId, int minPrice, int maxPrice) {
        List<Product> prodList;

        if(categoryId == 0) prodList = productRepository.findAllProdByPriceRange(minPrice,maxPrice);
        else                prodList = productRepository.findAllProdByCateIdByPriceRange(categoryId,minPrice,maxPrice);

        return getProductFilterDtoList("prod_price_range",prodList);
    }

    @Override
    public List<ProdFilterDTO> readAllProdByName(int categoryId, String prodName) {
        List<Product> prodList;

        if(categoryId == 0) prodList = productRepository.findAllProdByName(prodName);
        else                prodList = productRepository.findAllProdByCateIdByName(categoryId,prodName);

        return getProductFilterDtoList("prod_search_name",prodList);
    }


    @Override
    public List<ProductDTO> readAllProducts() {
        List<ProductDTO> productDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisKeyModel.PRODUCTS.getValue());
            if (data == null){
                List<Product> productList = productRepository.findAll();
                for (Product product : productList) {
                    ProductDTO productDTO = new ProductDTO();

                    productDTO.setId(product.getId());
                    productDTO.setName(product.getName());
                    productDTO.setPrice_origin(product.getOriginPrice());
                    productDTO.setPrice_discount(product.getDiscountPrice());
                    productDTO.setInventory(product.getInventory());
                    productDTO.setSold_qty(product.getSoldQty());
                    productDTO.setView_qty(product.getView_qty());
                    productDTO.setDescription(product.getDescription());
                    if(product.getCreate_date() != null){
                        productDTO.setCreateDate(new SimpleDateFormat("dd-MM-yyyy").format(product.getCreate_date()));
                    }
                    if(product.getCategory() != null){
                        productDTO.setCategoryId(product.getCategory().getId());
                    }
                    productDtoList.add(productDTO);
                }
                redisTemplate.opsForValue().set(RedisKeyModel.PRODUCTS.getValue(),gson.toJson(productDtoList));
                redisTemplate.expire(RedisKeyModel.PRODUCTS.getValue(),30, TimeUnit.MINUTES);
            } else {
                productDtoList = gson.fromJson(data, new TypeToken<List<ProductDTO>>(){}.getType());
            }
            LOGGER.info("Read product list successfully");
            return productDtoList;
        } catch (Exception e){
            LOGGER.error("Failed to read product list : {}",e.getMessage());
            return null;
        }
    }

    @Override
    public ProductDTO readProductById(int productId) {
        ProductDTO productDTO= new ProductDTO();

        try {
            Product product= productRepository.findById(productId);
            if (product == null){
                LOGGER.error("Product with Id '{}' does not exits",productId);
                return null;
            }
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice_origin(product.getOriginPrice());
            productDTO.setPrice_discount(product.getDiscountPrice());
            productDTO.setInventory(product.getInventory());
            productDTO.setSold_qty(product.getSoldQty());
            productDTO.setView_qty(product.getView_qty());
            productDTO.setDescription(product.getDescription());
            if(product.getCreate_date() != null){
                productDTO.setCreateDate(new SimpleDateFormat("dd-MM-yyyy").format(product.getCreate_date()));
            }
            if(product.getCategory() != null){
                productDTO.setCategoryId(product.getCategory().getId());
            }
            LOGGER.info("Read product info with Id '{}' successfully by User",productId);
            return productDTO;
        } catch (Exception e){
            LOGGER.error("Failed to read product info with Id '{}' by User : {}",productId,e.getMessage());
            return null;
        }
    }

    @Override
    public List<String> readAllProductImage(int productId, int categoryId) {
        List<String> imageList = new ArrayList<>();

        for (int count=0,i=1;i<20;i++,count++){
            Path imagePath = Paths.get(getImagePath(categoryId)+productId+"-"+i+".png");
            if(Files.exists(imagePath)){
                imageList.add(imagePath.toString());
                count=0;
            }
            if(count > 2) break;
        }

        return imageList;
    }

    @Override
    public boolean updateProductById(int prodId,ProductDTO productDTO) {
        try {
            Product product = productRepository.findById(prodId);
            if (product == null){
                LOGGER.error("Product with Id '{}' does not exits",prodId);
                return false;
            }
            product.setName(productDTO.getName());
            product.setOriginPrice(productDTO.getPrice_origin());
            product.setDiscountPrice(productDTO.getPrice_discount());
            product.setInventory(productDTO.getInventory());
            product.setSoldQty(productDTO.getView_qty());
            product.setView_qty(productDTO.getView_qty());
            product.setCreate_date(new SimpleDateFormat("yyyy-MM-dd").parse(productDTO.getCreateDate()));
            product.setDescription(productDTO.getDescription());
            product.setCategory(new Category(productDTO.getCategoryId()));

            productRepository.save(product);
            clearRedisData();
            LOGGER.info("Product with Id '{}' has been updated successfully",prodId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to update product with Id '{}' : {}",prodId,e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProductById(int prodId) {
        try {
            productRepository.deleteById(prodId);
            clearRedisData();
            LOGGER.info("Product with Id '{}' has been deleted successfully",prodId);
            return true;
        } catch (Exception e){
            LOGGER.error("Failed to delete product with Id '{}' : {}",prodId,e.getMessage());
            return false;
        }
    }

}
