package com.rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value(("${rabbitmq.routingkey.name"))
    private String routingKey;

    @Value("${rabbitmq.queue.jsonqueue}")
    private String jsonQueueName;

    @Value("${rabbitmq.routingkey.json.key}")
    private String routingJsonKey;

//    create a queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

//    create an exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }

//    binding the queue with routng key
    @Bean
    public Binding binding(){
        Binding with = BindingBuilder.bind(queue()).to(exchange()).with("demo_key");
        return with;
    }

    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueueName);
    }

    @Bean
    public Binding bindingJson(){
        return BindingBuilder.bind(jsonQueue())
                .to(exchange())
                .with(routingJsonKey);
    }

//    to support the json message , we have to configure the json support to rabbitTemplate
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}
