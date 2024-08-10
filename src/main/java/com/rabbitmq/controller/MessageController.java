package com.rabbitmq.controller;

import com.rabbitmq.config.RabbitMQConfig;
import com.rabbitmq.dto.MyCustomProducerDto;
import com.rabbitmq.dto.ProducerDto;
import com.rabbitmq.dto.User;
import com.rabbitmq.producer.RabbitMQJsonProducer;
import com.rabbitmq.producer.RabbitMQProduce;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rabbit")
public class MessageController {

    @Autowired
    private RabbitMQProduce rabbitMQProduce;

    @Autowired
    private RabbitMQJsonProducer rabbitMQJsonProducer;

    @Autowired
    private AmqpTemplate amqpTemplate;

//    public MessageController(RabbitMQProduce rabbitMQProduce) {
//        this.rabbitMQProduce = rabbitMQProduce;
//    }

    @GetMapping("/publish/{message}")
    public ResponseEntity<String> sendMessage(@PathVariable String message) {
        rabbitMQProduce.send(message);
        return ResponseEntity.ok("message sent to rabbitMq");
    }
//    send json message

    @PostMapping("/user")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        rabbitMQJsonProducer.send(user);
        return ResponseEntity.ok("json message sent to rabbitMq");

    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MyCustomProducerDto myCustomProducerDto) {

        boolean isMessageSendToAdminQueue = false;
        boolean isMessageSendToFinanceQueue = false;
        boolean isMessageSendToMarketingQueue = false;

        if (myCustomProducerDto.isSendAdminQueue()) {
            amqpTemplate.convertAndSend(
                    RabbitMQConfig.DIRECT_EXCHANGE,
                    RabbitMQConfig.ADMIN_KEY,
                    "my message, direct-excahnge using "+ RabbitMQConfig.ADMIN_KEY
            );
            isMessageSendToAdminQueue = true;
        }
        if (myCustomProducerDto.isSendFinanceQueue()){
            amqpTemplate.convertAndSend(
                    RabbitMQConfig.DIRECT_EXCHANGE,
                    RabbitMQConfig.FINANCE_KEY,
                    "MyFinance message, direct-excahnge using "+RabbitMQConfig.FINANCE_KEY
            );
            isMessageSendToFinanceQueue = true;
        }
        if (myCustomProducerDto.isSendMarketingQueue()){
            amqpTemplate.convertAndSend(
                    RabbitMQConfig.DIRECT_EXCHANGE,
                    RabbitMQConfig.MARKETING_KEY,
                    "my marketing message, direct-excahnge using "+ RabbitMQConfig.MARKETING_KEY
            );
            isMessageSendToMarketingQueue = true;
        }

        return ResponseEntity.ok("message sent to rabbitMq");
    }

    @GetMapping("/fanout/{message}")
    public ResponseEntity<String> fanOutExchange(@PathVariable String message){
        amqpTemplate.convertAndSend(RabbitMQConfig.FAN_OUT_EXCHANGE,"send message to rabbitMq",message);
        return ResponseEntity.ok("message has beeen send to rabbitMq");
    }


}
