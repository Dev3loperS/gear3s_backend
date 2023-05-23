package cybersoft.java20.gear3s.rabbitmq.service;

import cybersoft.java20.gear3s.rabbitmq.ProductRepository;
import cybersoft.java20.gear3s.rabbitmq.entity.Product;
import cybersoft.java20.gear3s.rabbitmq.service.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean confirmProductInventory(int productId, int requestNum) {
        try{
            Product product = productRepository.findById(productId);
            if(product == null || requestNum > product.getInventory()){
                return false;  // Product does not exits or Quantity requested exceeds inventory
            }
            product.setInventory(product.getInventory() - requestNum);
            product.setSoldQty(product.getSoldQty() + requestNum);
            productRepository.save(product);
            redisTemplate.delete("products");
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
