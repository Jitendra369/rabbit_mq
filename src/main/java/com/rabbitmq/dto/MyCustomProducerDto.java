package com.rabbitmq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyCustomProducerDto extends  ProducerDto{
    private boolean sendAdminQueue;
    private boolean sendFinanceQueue;
    private boolean sendMarketingQueue;
}
