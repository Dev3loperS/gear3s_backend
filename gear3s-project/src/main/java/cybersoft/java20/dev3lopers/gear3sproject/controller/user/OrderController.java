package cybersoft.java20.dev3lopers.gear3sproject.controller.user;

import cybersoft.java20.dev3lopers.gear3sproject.dto.OrderDTO;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.OrderService;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;



    @GetMapping("")
    public ResponseEntity<?> getAllOrder() {

        List<OrderDTO> list = orderService.findAll();
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setData(list);
        basicResponse.setMessage("true");
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/countAllById")
    public ResponseEntity<?> countAllById() {

        int total = orderService.countAllOrder();
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setData(total);
        basicResponse.setMessage("true");
        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam int id ) {
        OrderDTO list = orderService.findById(id);
        BasicResponse basicResponse = new BasicResponse();
        if (list !=null)
        {
            basicResponse.setData(list);
            basicResponse.setMessage("true");
        }else
        {
            basicResponse.setData(list);
            basicResponse.setMessage("false");
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> insertOrder(@RequestBody OrderDTO orderDTO) {

        BasicResponse basicResponse = new BasicResponse();
        boolean isSuccess = orderService.insertNewOrder(orderDTO);
        if (isSuccess) {
            basicResponse.setMessage("Insert successfully order");

            basicResponse.setData(true);
        } else {
            basicResponse.setMessage("Insert unsuccessfully order");
            basicResponse.setData(false);
        }

        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteOrder(int id) {

        BasicResponse basicResponse = new BasicResponse();
        boolean iSuccess = orderService.deleteById(id);

        if (iSuccess) {
            basicResponse.setMessage("Delete successfully order");
            basicResponse.setData(iSuccess);
        } else {
            basicResponse.setMessage("Delete unsuccessfully order");
            basicResponse.setData(iSuccess);
        }


        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable ("id") int id , @RequestBody OrderDTO orderDTO) {

        BasicResponse basicResponse = new BasicResponse();
        boolean iSuccess = orderService.updateById(id,orderDTO);

        if (iSuccess) {
            basicResponse.setMessage("Update successfully order");
            basicResponse.setData(iSuccess);
        } else {
            basicResponse.setMessage("Update unsuccessfully order");
            basicResponse.setData(iSuccess);
        }


        return new ResponseEntity<>(basicResponse, HttpStatus.OK);
    }
}
