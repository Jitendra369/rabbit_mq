package com.rabbitmq.dto;

import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String password;
    private String email;
    private String phone;
}
