package com.example.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetails {

    private Integer orderDetailId;
    private Integer orderId;
//    private Integer cartId;
    private Integer memberId;
    private Integer productId;
    private Integer productQty;
    private Integer productPrice;

    public OrderDetails(Integer orderDetailId, Integer orderId, Integer memberId, Integer productId, Integer productQty, Integer productPrice) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.memberId = memberId;
        this.productId = productId;
        this.productQty = productQty;
        this.productPrice = productPrice;
    }

    //    public OrderDetails(Integer orderDetailId, Integer orderId, Integer cartId, Integer memberId, Integer productId, Integer productQty, Integer productPrice) {
//        this.orderDetailId = orderDetailId;
//        this.orderId = orderId;
//        this.cartId = cartId;
//        this.memberId = memberId;
//        this.productId = productId;
//        this.productQty = productQty;
//        this.productPrice = productPrice;
//    }
}
