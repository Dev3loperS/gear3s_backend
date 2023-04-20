package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderItemService {
    List<OrderItemDTO> findAll () ;

    boolean  deleteById (int id ) ;

    OrderItemDTO findByOrderId (int id ) ;

    boolean addNewOrderItem (OrderItemDTO orderItemDTO) ;
    boolean updateOrderItem (int id , OrderItemDTO orderItemDTO ) ;

}
