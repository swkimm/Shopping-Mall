package com.example.shop.request.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateQtyInCartRequest {

    private Integer cartId;
    private Integer productId;
    private Integer memberId;
    private Integer productQty;

    public UpdateQtyInCartRequest(Integer cartId, Integer productId, Integer memberId, Integer productQty) {
        this.cartId = cartId;
        this.productId = productId;
        this.memberId = memberId;
        this.productQty = productQty;
    }
}
