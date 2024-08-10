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

    public static final String MARKETING_QUEUE = "marketing_queue";
    public static final String FINANCE_QUEUE = "finance_queue";
    public static final String ADMIN_QUEUE = "admin_queue";
    public static final String DIRECT_EXCHANGE = "direct_exchange";
    public static final String MARKETING_KEY = "marketing_key";
    public static final String FINANCE_KEY = "finance_key";
    public static final String ADMIN_KEY = "admin_key";
    public static final String FAN_OUT_EXCHANGE = "fan_out_exchange";
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

    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueueName);
    }

    @Bean
    public Queue marketingQueue(){
        return new Queue(MARKETING_QUEUE);
    }

    @Bean
    public Queue financeQueue(){
        return new Queue(FINANCE_QUEUE);
    }

    @Bean
    public Queue adminQueue(){
        return new Queue(ADMIN_QUEUE);
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FAN_OUT_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    Binding marketingBinding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(directExchange()).to(directExchange).with(MARKETING_KEY);
    }

    @Bean
    Binding financeBinding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(directExchange()).to(directExchange).with(FINANCE_KEY);
    }
    @Bean
    Binding adminBinding(Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(directExchange()).to(directExchange).with(RabbitMQConfig.ADMIN_KEY);
    }

//    creating queue for fanout exchange , it dosen't required routing , it's like broadcasting
    @Bean
    Binding fanoutAdminBinding(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(adminQueue()).to(fanoutExchange);
    }

    @Bean
    Binding fanoutMarketingBinding(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(marketingQueue()).to(fanoutExchange);
    }

    @Bean
    Binding fanoutFinanceBinding(Queue queue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(financeQueue()).to(fanoutExchange);
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
