package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.OrderItemDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {
    @Autowired
    OrderItemService orderItemService ;
    @GetMapping("")
    public ResponseEntity<?>getAllOrderItem ()
    {

        BasicResponse basicResponse = new BasicResponse();
        List<OrderItemDTO> orderItemDTOS = orderItemService.findAll();
        basicResponse.setMessage("true");
        basicResponse.setData(orderItemDTOS);
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/deleteById")
    public ResponseEntity<?>deleteOrderItem (@RequestParam int id )
    {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = orderItemService.deleteById(id);
        if (isSuccess)
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Delete order item successfully id : " +id);
        }else
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Delete order item unsuccessfully id : " +id);
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/findByOrderId")
    public ResponseEntity<?>findByOrderId (@RequestParam int id )
    {
        BasicResponse basicResponse = new BasicResponse();
        OrderItemDTO isSuccess = orderItemService.findByOrderId(id);

        if (isSuccess!=null)
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Founded order item successfully id : " +id);
        }else
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Founded order item unsuccessfully id : " +id);
        }
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?>addNewOrderItem (@RequestBody OrderItemDTO orderItemDTO )
    {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = orderItemService.addNewOrderItem(orderItemDTO);
        if (isSuccess)
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Add order item successfully" );
        }else
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Add order item unsuccessfully" );
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?>updateOrderItem (@PathVariable int id , @RequestBody OrderItemDTO orderItemDTO )
    {
        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = orderItemService.updateOrderItem(id , orderItemDTO);
        if (isSuccess)
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Update order item successfully id : "+id );
        }else
        {
            basicResponse.setData(isSuccess);
            basicResponse.setMessage("Update order item unsuccessfully id : "+id );
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

}
