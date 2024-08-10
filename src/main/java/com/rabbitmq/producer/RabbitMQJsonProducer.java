package com.rabbitmq.producer;

import com.rabbitmq.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQJsonProducer {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

//    @Value("${rabbitmq.queue.jsonqueue}")
//    private String jsonQueue;

    @Value(("${rabbitmq.routingkey.json.key}"))
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public RabbitMQJsonProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(User user) {
        log.info("Sending user: {}", user);
            rabbitTemplate.convertAndSend(exchange,routingKey,user);
    }
}
