package com.example.shop.request.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateDeliveryStatusRequest {

    private Integer paymentId;
    private Integer orderId;
    private String paymentStatus;

    public UpdateDeliveryStatusRequest(Integer paymentId, Integer orderId, String paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }
}
