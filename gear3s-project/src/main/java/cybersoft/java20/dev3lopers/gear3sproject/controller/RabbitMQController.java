package cybersoft.java20.dev3lopers.gear3sproject.controller;

import cybersoft.java20.dev3lopers.gear3sproject.payload.request.QueueRequest;
import cybersoft.java20.dev3lopers.gear3sproject.payload.response.BasicResponse;
import cybersoft.java20.dev3lopers.gear3sproject.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product/rabbitmq")
public class RabbitMQController {
    @Autowired
    ProductServiceImp productServiceImp;

    @PostMapping("")
    public ResponseEntity<?> sendMessage2Queue(@RequestBody QueueRequest queueRequest){

        return new ResponseEntity<>(new BasicResponse("RabbitMQ test",productServiceImp.confirmProdInventory(queueRequest)),HttpStatus.OK);
    }

}
