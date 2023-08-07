package com.example.shop.service;

import com.example.shop.mapper.CartMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.request.cart.AddToCartRequest;
import com.example.shop.request.cart.DeleteInCartRequest;
import com.example.shop.request.cart.UpdateQtyInCartRequest;
import com.example.shop.response.CartListResponse;
import com.example.shop.response.GetCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    public void addToCart(AddToCartRequest addToCartRequest) {
        cartMapper.addToCart(addToCartRequest);
    }

    public List<CartListResponse> getCartList(Integer memberId) {
        return cartMapper.getCartList(memberId);
    }

    public void updateQtyInCart(UpdateQtyInCartRequest request) {
        cartMapper.updateQtyInCart(request);
    }

    public Integer isInCart(Integer memberId, Integer pId) {
        if (cartMapper.isInCart(memberId, pId) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void deleteInCart(DeleteInCartRequest request) {
        cartMapper.deleteInCart(request);
    }

    public Integer totalPrice(Integer memberId) {
        return cartMapper.totalPrice(memberId);
    }


    public GetCartResponse getCartInfo(Integer memberId) {
        return cartMapper.getCartInfo(memberId);
    }

    public void deletePurchasedItemCart(Integer memberId, Integer orderId) {
        if (orderMapper.isInPayment(orderId) == true) {
            cartMapper.deletePurchasedItemCart(memberId);
        } else {
            System.out.println("주문이 아직 결제되지 않았습니다.");
        }

    }
}
