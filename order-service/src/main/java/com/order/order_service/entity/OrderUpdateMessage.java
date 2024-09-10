package com.order.order_service.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateMessage {
    private Long orderId;
    private String status;

    public OrderUpdateMessage(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}
