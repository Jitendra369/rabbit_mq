package com.rabbitmq.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String id;
    private String name;
    private String password;
    private String email;
    private String phone;
}
