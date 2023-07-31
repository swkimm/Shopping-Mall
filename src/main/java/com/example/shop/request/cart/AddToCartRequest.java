package com.example.shop.request.cart;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToCartRequest {

    private Integer pId;
    private Integer productQty;
    private Integer memberId;

    public AddToCartRequest(Integer pId, Integer quantity, Integer memberId) {
        this.pId = pId;
        this.productQty = quantity;
        this.memberId = memberId;
    }
}
