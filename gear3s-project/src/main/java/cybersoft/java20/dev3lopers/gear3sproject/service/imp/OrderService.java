package cybersoft.java20.dev3lopers.gear3sproject.service.imp;

import cybersoft.java20.dev3lopers.gear3sproject.dto.*;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAll ();
    boolean  insertNewOrder (OrderDTO orderDTO);

    boolean deleteById (int id ) ;
    boolean updateById(int id ,OrderDTO orderDTO );

    OrderDTO findById (int id ) ;

    List<CountOrdersGroupByYearDTO> countOrdersGroupByYear() ;
    List<CountOrdersGroupByMonthDTO> countOrdersGroupByMonth() ;

    List<SumTotalOrdersGroupByYearDTO> sumOrdersGroupByYear() ;
    List<SumTotalOrdersGroupByMonthDTO> sumOrdersGroupByMonth() ;

}
