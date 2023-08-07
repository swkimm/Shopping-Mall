package com.example.shop.request.order;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaveOrderRequest {


    private Integer orderId;
    private Integer memberId;
    private String productId;
    private String productQty;
    private String productPrice;


    public SaveOrderRequest(Integer orderId, Integer memberId, String productId, String productQty, String productPrice) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.productId = productId;
        this.productQty = productQty;
        this.productPrice = productPrice;
    }
}
