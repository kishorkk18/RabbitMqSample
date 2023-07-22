package com.rabbitmq.demo.publisher;

import com.rabbitmq.demo.config.MessagingConfig;
import com.rabbitmq.demo.dto.Order;
import com.rabbitmq.demo.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{myName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String myName) {
        order.setOrderId(UUID.randomUUID().toString());
       
        //payment service
        OrderStatus orderStatus = new OrderStatus(order, "PROCESS", "order placed succesfully in " + myName);
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
        return "Success !!";
    }
}
