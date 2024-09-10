package com.shipping.modle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateMessage {
    private String orderId;
    private String status;

    public OrderUpdateMessage(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }
}
