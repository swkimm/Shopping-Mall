package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentVerificationResponse {

    private String merchant_uid;
    private int amount;
    private boolean success;

    public PaymentVerificationResponse(String merchant_uid, int amount, boolean success) {
        this.merchant_uid = merchant_uid;
        this.amount = amount;
        this.success = success;
    }
}
