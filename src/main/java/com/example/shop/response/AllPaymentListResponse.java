package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AllPaymentListResponse {

    private Integer paymentId;
    private Integer orderId;
    private String productName;
    private String amount;
    private LocalDateTime paymentDate;
    private Integer invoice;
    private Integer memberId;
    private String name;
    private String address;
    private String deliveryRequest;
    private String paymentStatus;

    public AllPaymentListResponse(Integer paymentId, Integer orderId, String productName, String amount, LocalDateTime paymentDate, Integer invoice, Integer memberId, String name, String address, String deliveryRequest, String paymentStatus) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.productName = productName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.invoice = invoice;
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.deliveryRequest = deliveryRequest;
        this.paymentStatus = paymentStatus;
    }
}
