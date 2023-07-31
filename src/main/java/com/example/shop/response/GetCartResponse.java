package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Select;

@Data
@NoArgsConstructor
public class GetCartResponse {

    private Integer cartId;
    private Integer productId;
    private Integer memberId;
    private Integer productQty;
    private String brand;
    private String name;
    private String color;
    private String size;
    private Integer totalPrice;
    private Integer countProduct;

    public GetCartResponse(Integer cartId, Integer productId, Integer memberId, Integer productQty, String brand, String name, String color, String size, Integer totalPrice, Integer countProduct) {
        this.cartId = cartId;
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
