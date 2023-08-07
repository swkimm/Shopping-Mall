package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetOrderResponse {

    private Integer orderDetailId;
    private Integer orderId;
    private Integer productId;
    private Integer memberId;
    private Integer productQty;
    private String brand;
    private String name;
    private String color;
    private String size;
    private Integer totalPrice;
    private Integer countProduct;

    public GetOrderResponse(Integer orderDetailId, Integer orderId, Integer productId, Integer memberId, Integer productQty, String brand, String name, String color, String size, Integer totalPrice, Integer countProduct) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.memberId = memberId;
        this.productQty = productQty;
        this.brand = brand;
        this.name = name;
        this.color = color;
        this.size = size;
        this.totalPrice = totalPrice;
        this.countProduct = countProduct;
    }
}
