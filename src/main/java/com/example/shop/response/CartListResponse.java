package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartListResponse {

    private Integer pId;
    private String pBrand; // products
    private String pName; // products
    private String pPrice; // products
    private String pSize; // products
    private String pColor; // products
    private String pStock; // products
    private String pStatus; // products
    private Integer cartId; // cart
    private Integer productId; // cart
    private Integer memberId; // cart
    private Integer productQty; // cart
    private List<String> fileName; // productFile

    public CartListResponse(Integer pId, String pBrand, String pName, String pPrice, String pSize, String pColor, String pStock, String pStatus, Integer cartId, Integer productId, Integer memberId, Integer productQty, List<String> fileName) {
        this.pId = pId;
        this.pBrand = pBrand;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pSize = pSize;
        this.pColor = pColor;
        this.pStock = pStock;
        this.pStatus = pStatus;
        this.cartId = cartId;
        this.productId = productId;
        this.memberId = memberId;
        this.productQty = productQty;
        this.fileName = fileName;
    }
}
