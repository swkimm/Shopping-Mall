package com.example.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Delivery {

    private Integer deliveryId;
    private String status;

    public Delivery(Integer deliveryId, String status) {
        this.deliveryId = deliveryId;
        this.status = status;
    }
}
