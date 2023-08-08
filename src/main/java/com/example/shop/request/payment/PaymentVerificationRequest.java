package com.example.shop.request.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentVerificationRequest {

    private int amount;

    public PaymentVerificationRequest(int amount) {
        this.amount = amount;
    }
}
