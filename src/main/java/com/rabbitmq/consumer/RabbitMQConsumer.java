package com.rabbitmq.consumer;

import com.rabbitmq.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message){
        log.info("message received " + message);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.jsonqueue}"})
    public void consumeJson(User user){
        log.info("getting json message received " + user);
    }
}
