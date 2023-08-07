package com.example.shop.mapper;

import com.example.shop.request.cart.AddToCartRequest;
import com.example.shop.request.cart.DeleteInCartRequest;
import com.example.shop.request.cart.UpdateQtyInCartRequest;
import com.example.shop.response.CartListResponse;
import com.example.shop.response.GetCartResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("""
            INSERT INTO Cart (productId, memberId, productQty)
            VALUE (#{pId}, #{memberId}, #{productQty})
            """)
    void addToCart(AddToCartRequest addToCartRequest);

    @Select("""
            SELECT 
                p.pId
                , p.pBrand
                , p.pName
                , p.pPrice
                , p.pSize
                , p.pColor
                , p.pStock
                , c.cartId
                , c.memberId
                , c.productId
                , c.productQty
                , pf.fileName 
            FROM Products p
            LEFT JOIN 
                Cart c ON c.productId = p.pId
            LEFT JOIN 
                ProductFile pf ON pf.productId = p.pId
            WHERE c.memberId = #{memberId};
            """)
    @ResultMap("cartResultMap")
    List<CartListResponse> getCartList(Integer memberId);

    @Update("""
            UPDATE Cart
            SET 
                productQty = #{productQty}
            WHERE 
                memberId = #{memberId} 
                AND 
                cartId = #{cartId} 
                AND 
                productId = #{productId}
            """)
    void updateQtyInCart(UpdateQtyInCartRequest request);

    @Select("""
            SELECT COUNT(productId) FROM Cart
            WHERE memberId = #{memberId} AND productId = #{pId};
            """)
    Integer isInCart(Integer memberId, Integer pId);


    @Delete("""
            DELETE FROM Cart
            WHERE memberId = #{memberId} AND productId = #{productId} AND cartId = #{cartId}
            """)
    void deleteInCart(DeleteInCartRequest request);

    @Select("""
            SELECT SUM(p.pPrice * c.productQty) FROM Cart c
            LEFT JOIN Products p ON p.pId = c.productId
            WHERE memberId = #{memberId}
            """)
    Integer totalPrice(Integer memberId);

    @Select("""
            SELECT 
                c.*
                , p.pBrand AS brand
                , p.pName AS name
                , p.pColor AS color
                , p.pSize AS size
                , SUM(p.pPrice * c.productQty) AS totalPrice
                , COUNT(c.productId) AS countProduct
            FROM Cart c
            LEFT JOIN 
                Products p ON p.pId = c.productId
            WHERE 
                memberId = #{memberId};
            """)
    GetCartResponse getCartInfo(Integer memberId);

    @Delete("""
            DELETE FROM Cart 
            WHERE memberId = #{memberId}
            """)
    void deletePurchasedItemCart(Integer memberId);
}
