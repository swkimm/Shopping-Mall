package com.example.shop.request.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveOrderDetailRequest {

    private Integer orderId;
//    private Integer cartId;
    private Integer memberId;
    private Integer productId;
    private Integer productQty;
    private Integer productPrice;

    public SaveOrderDetailRequest(Integer orderId, Integer memberId, Integer productId, Integer productQty, Integer productPrice) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.productId = productId;
        this.productQty = productQty;
        this.productPrice = productPrice;
    }

    //    public SaveOrderDetail(Integer orderId, Integer cartId, Integer memberId, Integer productId, Integer productQty, Integer productPrice) {
//        this.orderId = orderId;
//        this.cartId = cartId;
//        this.memberId = memberId;
//        this.productId = productId;
//        this.productQty = productQty;
//        this.productPrice = productPrice;
//    }
}
