package com.user.User.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private String productId;
    private int quantity;
    private String status;
}
