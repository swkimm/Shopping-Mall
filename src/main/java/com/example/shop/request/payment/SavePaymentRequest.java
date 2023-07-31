package com.example.shop.request.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SavePaymentRequest {

    private Integer orderId;
    private String productName;
    private Integer amount;
    private LocalDateTime paymentDate;
    private Integer memberId;
    private String paymentStatus;
    private Integer invoice;
    private String deliveryRequest;
    private String email;
    private String name;
    private String phone;
    private String address;

    public SavePaymentRequest(Integer orderId, String productName, Integer amount, LocalDateTime paymentDate, Integer memberId, String paymentStatus, Integer invoice, String deliveryRequest, String email, String name, String phone, String address) {
        this.orderId = orderId;
        this.productName = productName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.memberId = memberId;
        this.paymentStatus = paymentStatus;
        this.invoice = invoice;
        this.deliveryRequest = deliveryRequest;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
