package com.example.shop.request.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteInCartRequest {

    private Integer memberId;
    private Integer productId;
    private Integer cartId;

    public DeleteInCartRequest(Integer memberId, Integer productId, Integer cartId) {
        this.memberId = memberId;
        this.productId = productId;
        this.cartId = cartId;
    }
}
