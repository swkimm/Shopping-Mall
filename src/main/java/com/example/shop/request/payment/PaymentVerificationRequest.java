package com.example.shop.request.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentVerificationRequest {

    private int paidAmount;

    public PaymentVerificationRequest(int paidAmount) {
        this.paidAmount = paidAmount;
    }
}
