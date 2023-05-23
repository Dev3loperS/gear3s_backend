package cybersoft.java20.dev3lopers.gear3sproject.service;

import com.google.gson.Gson;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.QueueRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Object sendMessage(QueueRequest message){
        Gson gson = new Gson();

        return rabbitTemplate.convertSendAndReceive(exchange,routingKey,gson.toJson(message));
    }
}
