package com.rabbitmq.consumer;

import com.rabbitmq.config.RabbitMQConfig;
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

    @RabbitListener(queues = RabbitMQConfig.MARKETING_QUEUE)
    public void marketingQueueListner(String message){
        log.info("message recieved from marketingQueue in Listener "+  message);
    }

    @RabbitListener(queues = RabbitMQConfig.ADMIN_QUEUE)
    public void adminQueueListner(String message){
        log.info("message recieved from AdminQueue Listener "+  message);
    }
}
