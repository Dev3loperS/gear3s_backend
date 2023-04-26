package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Category;
import cybersoft.java20.dev3lopers.gear3sproject.entity.Product;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductDesc;
import cybersoft.java20.dev3lopers.gear3sproject.entity.ProductProperty;
import cybersoft.java20.dev3lopers.gear3sproject.model.CategoryModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.ImagesModel;
import cybersoft.java20.dev3lopers.gear3sproject.model.RedisModel;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.FilterRequest;
import cybersoft.java20.dev3lopers.gear3sproject.repository.ProductRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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

    private String getImagePath(int categoryId) {
        if (categoryId == CategoryModel.KEYBOARD.getValue()) {
            return imagePath + ImagesModel.KEYBOARD.getValue();
        } else if (categoryId == CategoryModel.MOUSE.getValue()) {
            return imagePath + ImagesModel.MOUSE.getValue();
        } else if (categoryId == CategoryModel.MONITOR.getValue()) {
            return imagePath + ImagesModel.MONITOR.getValue();
        } else if (categoryId == CategoryModel.HEADSET.getValue()) {
            return imagePath + ImagesModel.HEADSET.getValue();
        } else if (categoryId == CategoryModel.LAPTOP.getValue()) {
            return imagePath + ImagesModel.LAPTOP.getValue();
        } else if (categoryId == CategoryModel.SPEAKER.getValue()) {
            return imagePath + ImagesModel.SPEAKER.getValue();
        } else {
            return "";
        }
    }

    // For Admin page --------------------------------------------------------------------------------------------------
    @Override
    public boolean createProduct(ProdCreateDTO prodCreateDTO) {
        String localDate = LocalDate.now().toString();

        try {
            Product product = new Product();
            product.setName(prodCreateDTO.getName());
            product.setOriginPrice(prodCreateDTO.getPrice_origin());
            product.setDiscountPrice(prodCreateDTO.getPrice_discount());
            product.setInventory(prodCreateDTO.getInventory());
            product.setSoldQty(0);
            product.setView_qty(0);
            product.setDescription(prodCreateDTO.getDescription());
            product.setCreate_date(new SimpleDateFormat("yyyy-MM-dd").parse(localDate));
            product.setCategory(new Category(prodCreateDTO.getCategoryId()));

            productRepository.save(product);
            redisTemplate.delete(RedisModel.PRODUCTS.getValue());
            LOGGER.info("Created new product successfully");
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to create product : {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<ProductDTO> readAllProducts() {
        List<ProductDTO> productDtoList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            String data = (String) redisTemplate.opsForValue().get(RedisModel.PRODUCTS.getValue());
            if (data == null) {
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
                    if (product.getCreate_date() != null) {
                        productDTO.setCreateDate(new SimpleDateFormat("dd-MM-yyyy").format(product.getCreate_date()));
                    }
                    if (product.getCategory() != null) {
                        productDTO.setCategoryId(product.getCategory().getId());
                    }
                    productDtoList.add(productDTO);
                }
                redisTemplate.opsForValue().set(RedisModel.PRODUCTS.getValue(), gson.toJson(productDtoList));
                redisTemplate.expire(RedisModel.PRODUCTS.getValue(), 30, TimeUnit.MINUTES);
            } else {
                productDtoList = gson.fromJson(data, new TypeToken<List<ProductDTO>>() {
                }.getType());
            }
            LOGGER.info("Read product list successfully");
            return productDtoList;
        } catch (Exception e) {
            LOGGER.error("Failed to read product list : {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ProductDTO readProductById(int productId) {
        ProductDTO productDTO = new ProductDTO();

        try {
            Product product = productRepository.findById(productId);
            if (product == null) {
                LOGGER.error("Product with Id '{}' does not exits", productId);
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
            if (product.getCreate_date() != null) {
                productDTO.setCreateDate(new SimpleDateFormat("dd-MM-yyyy").format(product.getCreate_date()));
            }
            if (product.getCategory() != null) {
                productDTO.setCategoryId(product.getCategory().getId());
            }
            updateProductView(productId);
            LOGGER.info("Read product info with Id '{}' successfully by User", productId);
            return productDTO;
        } catch (Exception e) {
            LOGGER.error("Failed to read product info with Id '{}' by User : {}", productId, e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateProduct(ProductDTO productDTO) {
        try {
            Product product = productRepository.findById(productDTO.getId());
            if (product == null) {
                LOGGER.error("Product with Id '{}' does not exits", productDTO.getId());
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
            redisTemplate.delete(RedisModel.PRODUCTS.getValue());
            LOGGER.info("Product with Id '{}' has been updated successfully", productDTO.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to update product with Id '{}' : {}", productDTO.getId(), e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteProductById(int prodId) {
        try {
            productRepository.deleteById(prodId);
            redisTemplate.delete(RedisModel.PRODUCTS.getValue());
            LOGGER.info("Product with Id '{}' has been deleted successfully", prodId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete product with Id '{}' : {}", prodId, e.getMessage());
            return false;
        }
    }

    // For User page ---------------------------------------------------------------------------------------------------
    @Override
    public List<ProdShortDTO> readAllProdAfterFilter(FilterRequest request) {
        List<ProdShortDTO> prodDtoList = new ArrayList<>();

        // Sort by type
        List<Product> prodSortList = sortProdByType(request.getSortType(), request.getCategoryId());

        // Filter by price range
        List<Product> prodRangeFilterList = new ArrayList<>();
        if (prodSortList != null && request.getMinPrice() >= 0 && request.getMaxPrice() >= 0
                && request.getMinPrice() <= request.getMaxPrice()) {
            for (Product prod : prodSortList) {
                if (prod.getDiscountPrice() >= request.getMinPrice() && prod.getDiscountPrice() <= request.getMaxPrice()) {
                    prodRangeFilterList.add(prod);
                }
            }
        } else {
            prodRangeFilterList = prodSortList;
        }

        // Filter by property
        List<Product> prodPropFilterList = new ArrayList<>();
        if (prodRangeFilterList != null && request.getCategoryId() != 0) {
            for (Product product : prodRangeFilterList) {
                List<ProductProperty> prodPropList = new ArrayList<>(product.getListProdProperty());
                if (filterProdByProperty(prodPropList, request.getPropDescList())) {
                    prodPropFilterList.add(product);
                }
            }
        } else {
            prodPropFilterList = prodRangeFilterList;
        }

        return getProductFilterDtoList(request.getSortType(), prodPropFilterList);
    }


    private List<Product> sortProdByType(String type, int categoryId) {
        switch (type) {
            case "popular":
                return readAllProdOrderByView(categoryId);
            case "latest":
                return readAllProdOrderByDate(categoryId);
            case "topSales":
                return readAllProdOrderBySold(categoryId);
            case "descPrice":
                return readAllProdOrderByPriceDesc(categoryId);
            case "ascPrice":
                return readAllProdOrderByPriceAsc(categoryId);
            default:
                return null;
        }
    }


    private List<Product> readAllProdOrderByView(int categoryId) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdOrderByViewQty();
        else prodList = productRepository.findAllProdByCateIdOrderByViewQty(categoryId);

        return prodList;
    }


    private List<Product> readAllProdOrderByDate(int categoryId) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdOrderByCreateDate();
        else prodList = productRepository.findAllProdByCateIdOrderByCreateDate(categoryId);

        return prodList;
    }

    private List<Product> readAllProdOrderBySold(int categoryId) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdOrderBySoldQty();
        else prodList = productRepository.findAllProdByCateIdOrderBySoldQty(categoryId);

        return prodList;
    }

    private List<Product> readAllProdOrderByPriceDesc(int categoryId) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdOrderByPriceDesc();
        else prodList = productRepository.findAllProdByCateIdOrderByPriceDesc(categoryId);

        return prodList;
    }

    private List<Product> readAllProdOrderByPriceAsc(int categoryId) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdOrderByPriceAsc();
        else prodList = productRepository.findAllProdByCateIdOrderByPriceAsc(categoryId);

        return prodList;
    }

    private boolean filterProdByProperty(List<ProductProperty> prodPropList, List<FilterPropDescDTO> filterPropDescDtoList) {
        for (ProductProperty productProperty : prodPropList) {
            ProductDesc productDesc = productProperty.getProduct_desc();
            for (FilterPropDescDTO propDesc : filterPropDescDtoList) {
                if (propDesc.getPropertyId() == productDesc.getCategory_property().getId()) {
                    boolean isMatched = false;
                    for (Integer descId : propDesc.getDescId()) {
                        if (descId == 0 || descId == productDesc.getId()) {
                            isMatched = true;
                            break;
                        }
                    }
                    if (!isMatched) return false;
                    break;
                }
            }
        }
        return true;
    }

    private List<ProdShortDTO> getProductFilterDtoList(String type, List<Product> prodList) {
        List<ProdShortDTO> prodDtoList = new ArrayList<>();
        try {
            for (Product product : prodList) {
                ProdShortDTO productDTO = new ProdShortDTO();

                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setOriginPrice(product.getOriginPrice());
                productDTO.setDiscountPrice(product.getDiscountPrice());
                productDTO.setSoldQty(product.getSoldQty());
                if (product.getCategory() != null) {
                    productDTO.setCategoryId(product.getCategory().getId());
                    productDTO.setImage(getImagePath(product.getCategory().getId()) + product.getId() + "-1.png");
                }
                prodDtoList.add(productDTO);
            }
            LOGGER.info("Read product list order by '{}' successfully", type);
            return prodDtoList;
        } catch (Exception e) {
            LOGGER.error("Failed to read product list order by '{}' : {}", type, e.getMessage());
            return null;
        }
    }

    @Override
    public List<ProdShortDTO> readAllProdByName(int categoryId, String prodName) {
        List<Product> prodList;

        if (categoryId == 0) prodList = productRepository.findAllProdByName(prodName);
        else prodList = productRepository.findAllProdByCateIdByName(categoryId, prodName);

        return getProductFilterDtoList("searchName", prodList);
    }

    @Override
    public boolean confirmOrder(int productId, int requestNum) {
        try {
            Product product = productRepository.findById(productId);
            if (product == null) {
                LOGGER.error("Product with Id '{}' does not exits", productId);
                return false;
            }
            if (requestNum > product.getInventory()) {
                LOGGER.error("Quantity requested exceeds inventory of product with Id '{}'", productId);
                return false;
            }
            product.setInventory(product.getInventory() - requestNum);
            product.setSoldQty(product.getSoldQty() + requestNum);

            productRepository.save(product);
            redisTemplate.delete(RedisModel.PRODUCTS.getValue());
            LOGGER.info("Updated soldQty of product with Id '{}' successfully", productId);
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to update soldQty of product with Id '{}' : {}", productId, e.getMessage());
            return false;
        }
    }

    @Override
    public void updateProductView(int productId) {
        try {
            Product product = productRepository.findById(productId);
            if (product == null) {
                LOGGER.error("Product with Id '{}' does not exits", productId);
                return;
            }
            product.setView_qty(product.getView_qty() + 1);

            productRepository.save(product);
            redisTemplate.delete(RedisModel.PRODUCTS.getValue());
            LOGGER.info("Updated viewQty of product with Id '{}' successfully", productId);
        } catch (Exception e) {
            LOGGER.error("Failed to update viewQty of product with Id '{}' : {}", productId, e.getMessage());
        }
    }

    @Override
    public List<ProdPropDescDTO> readProdPropDescById(int productId) {
        List<ProdPropDescDTO> propDescDtoList = new ArrayList<>();

        try {
            Product product = productRepository.findById(productId);
            if (product == null) {
                LOGGER.error("Product with Id '{}' does not exits", productId);
                return null;
            }
            List<ProductProperty> prodPropList = new ArrayList<>(product.getListProdProperty());
            for (ProductProperty prodProp : prodPropList) {
                ProdPropDescDTO propDescDto = new ProdPropDescDTO();
                if (prodProp.getProduct_desc().getCategory_property() != null) {
                    propDescDto.setProperty(prodProp.getProduct_desc().getCategory_property().getName());
                }
                if (prodProp.getProduct_desc() != null) {
                    propDescDto.setDescription(prodProp.getProduct_desc().getDescription());
                }
                propDescDtoList.add(propDescDto);
            }
            if (propDescDtoList.size() > 1) {
                for (int i = 0; i < propDescDtoList.size(); i++) {
                    if ("Hãng sản xuất".equals(propDescDtoList.get(i).getProperty())) {
                        if (i != 0) Collections.swap(propDescDtoList, 0, i);
                        ;
                        break;
                    }
                }
            }
            LOGGER.info("Read property description of product with Id '{}' successfully", productId);
            return propDescDtoList;
        } catch (Exception e) {
            LOGGER.error("Failed to read property description of product with Id '{}' : {}", productId, e.getMessage());
            return null;
        }
    }


}
