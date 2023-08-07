package com.example.shop.controller;


import com.example.shop.request.cart.AddToCartRequest;
import com.example.shop.request.cart.DeleteInCartRequest;
import com.example.shop.request.cart.UpdateQtyInCartRequest;
import com.example.shop.response.CartListResponse;
import com.example.shop.response.GetCartResponse;
import com.example.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/cart/addToCart/{pId}")
    public void addToCart(@PathVariable Integer pId, @RequestBody AddToCartRequest addToCartRequest) {
        System.out.println("pId = " + pId);
        addToCartRequest.setPId(pId);
        System.out.println("addToCartRequest = " + addToCartRequest);
        cartService.addToCart(addToCartRequest);
    }

    @PostMapping("/cart/getCartList")
    public List<CartListResponse> getCartList(@RequestBody Map<String, Object> request) {
        Integer memberId = (Integer) request.get("memberId");
        return cartService.getCartList(memberId);
    }

    @PostMapping("/cart/updateQtyInCart")
    public void updateQtyInCart(@RequestBody UpdateQtyInCartRequest request) {
        System.out.println("request = " + request);
        cartService.updateQtyInCart(request);
    }

    @PostMapping("/cart/isInCart/{pId}")
    public Integer isInCart(@PathVariable Integer pId, @RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        return cartService.isInCart(memberId, pId);
    }

    @PostMapping("/cart/deleteInCart")
    public void deleteInCart(@RequestBody DeleteInCartRequest request) {

        System.out.println("request = " + request);
        cartService.deleteInCart(request);
    }

    @PostMapping("/cart/totalPrice")
    public Integer totalPrice(@RequestBody Map<String, Object> request) {
        Integer memberId = (Integer) request.get("memberId");
        System.out.println("memberId = " + memberId);
        return cartService.totalPrice(memberId);
    }

    @PostMapping("/cart/getCartInfo")
    public GetCartResponse getCartInfo(@RequestBody Map<String, Integer> request) {
        System.out.println("request = " + request);
        Integer memberId = request.get("memberId");
        return cartService.getCartInfo(memberId);
    }

    @PostMapping("/cart/deletePurchasedItemCart")
    public void deletePurchasedItemCart(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        Integer orderId = request.get("orderId");
        cartService.deletePurchasedItemCart(memberId, orderId);

    }


}
