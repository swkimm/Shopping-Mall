package com.example.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cart {

    private Integer cartId;
    private Integer productId;
    private Integer memberId;
    private Integer productQty;

    public Cart(Integer cartId, Integer productId, Integer memberId, Integer productQty) {
        this.cartId = cartId;
        this.productId = productId;
        this.memberId = memberId;
        this.productQty = productQty;
    }
}
