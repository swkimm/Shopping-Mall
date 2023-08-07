package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class CompleteOrderListResponse {

    private Integer orderId;
    private Integer memberId;
    private String productName;
    private Integer productId;
    private Integer productQty;
    private Integer productPrice;
    private String paymentStatus;
    private LocalDateTime paymentDate;
    private List<String> fileName;

    public CompleteOrderListResponse(Integer orderId, Integer memberId, String productName, Integer productId, Integer productQty, Integer productPrice, String paymentStatus, LocalDateTime paymentDate, List<String> fileName) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.productName = productName;
        this.productId = productId;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.fileName = fileName;
    }
}
