package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentVerificationResponse {

    private String impUid;
    private int paidAmount;
    private boolean success;

    public PaymentVerificationResponse(String impUid, int paidAmount, boolean success) {
        this.impUid = impUid;
        this.paidAmount = paidAmount;
        this.success = success;
    }
}
