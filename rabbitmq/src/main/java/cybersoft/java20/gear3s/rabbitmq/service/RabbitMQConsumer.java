package cybersoft.java20.gear3s.rabbitmq.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cybersoft.java20.gear3s.rabbitmq.payload.QueueRequest;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ProductServiceImp productServiceImp;

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void receiveMessage(@Header(value = AmqpHeaders.REPLY_TO, required = false) String senderId,
                               @Header(value = AmqpHeaders.CORRELATION_ID, required = false) String correlationId,
                                String requestMessage){
        Gson gson = new Gson();
        try {
            System.out.println("Request message: " + requestMessage);
            if(senderId != null && correlationId != null){
                //Thread.sleep(1300);
                QueueRequest queueRequest = gson.fromJson(requestMessage,new TypeToken<QueueRequest>(){}.getType());

                boolean responseResult = productServiceImp.confirmProductInventory(queueRequest.getProductId(),
                                                                           queueRequest.getRequestNums());

                rabbitTemplate.convertAndSend(senderId,responseResult,message -> {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setCorrelationId(correlationId);

                    return message;
                });
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
