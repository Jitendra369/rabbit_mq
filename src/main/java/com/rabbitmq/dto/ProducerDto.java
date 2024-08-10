package com.rabbitmq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerDto {

    private boolean exchangeName;
    private boolean routingKey;
    private String messageData;

}
