package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAll ();
    boolean  insertNewOrder (OrderDTO orderDTO);

    boolean deleteById (int id ) ;
    boolean updateById(int id ,OrderDTO orderDTO );

    OrderDTO findById (int id ) ;

    int countAllOrder() ;
}
