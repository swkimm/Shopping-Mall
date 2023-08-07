package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SuccessPaymentResponse {

    private Integer invoice;
    private Integer amount;
    private Integer orderId;

    public SuccessPaymentResponse(Integer invoice, Integer amount, Integer orderId) {
        this.invoice = invoice;
        this.amount = amount;
        this.orderId = orderId;
    }
}
