package com.order.order_service.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private String productId;
    private int quantity;
    private String status;
    private String productName;
}
