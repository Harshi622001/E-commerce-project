package com.shipping.modle;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String productId;
    private int quantity;
    private String userId;
    private String status;

}


