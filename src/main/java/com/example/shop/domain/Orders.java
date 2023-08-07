package com.example.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Orders {

    private Integer orderId;
    private Integer memberId;
    private LocalDateTime orderDate;
    private String orderStatus;

    public Orders(Integer orderId, Integer memberId, LocalDateTime orderDate, String orderStatus) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
