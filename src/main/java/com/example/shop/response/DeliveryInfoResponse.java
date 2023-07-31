package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryInfoResponse {

    private Integer orderId;
    private Integer invoice;
    private String productName;
    private String paymentStatus;
    private String deliveryRequest;
    private String email;
    private String name;
    private String address;

    public DeliveryInfoResponse(Integer orderId, Integer invoice, String productName, String paymentStatus, String deliveryRequest, String email, String name, String address) {
        this.orderId = orderId;
        this.invoice = invoice;
        this.productName = productName;
        this.paymentStatus = paymentStatus;
        this.deliveryRequest = deliveryRequest;
        this.email = email;
        this.name = name;
        this.address = address;
    }
}
