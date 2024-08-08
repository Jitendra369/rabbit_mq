package com.rabbitmq.controller;

import com.rabbitmq.dto.User;
import com.rabbitmq.producer.RabbitMQJsonProducer;
import com.rabbitmq.producer.RabbitMQProduce;
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
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
        rabbitMQJsonProducer.send(user);
        return ResponseEntity.ok("json message sent to rabbitMq");

    }
}
