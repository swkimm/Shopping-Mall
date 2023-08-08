package com.example.shop.mapper;

import com.example.shop.domain.OrderDetails;
import com.example.shop.response.CompleteOrderListResponse;
import com.example.shop.response.GetOrderResponse;
import com.example.shop.response.OrderMemberResponse;
import com.example.shop.response.OrdersByDateRangeResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {


    @Select("""
            SELECT memberId, name, email, phone, address 
            FROM Members
            WHERE memberId = #{memberId};
            """)
    OrderMemberResponse getOrderMemberInfo(Integer memberId);

    @Insert("""
            INSERT INTO Orders (memberId)
            VALUES (#{memberId})
            """)
    void saveOrder(Integer memberId);

    @Select("""
            SELECT MAX(orderId) 
            FROM Orders
            WHERE memberId = #{memberId};
            """)
    Integer getOrderId(Integer memberId);

    @Insert("""
            INSERT INTO OrderDetails (
                orderId
                , memberId
                , productId
                , productQty
                , productPrice )
            VALUES (
                #{orderId} 
                , #{memberId}
                , #{productId}
                , #{productQty}
                , #{productPrice} )
            """)
    void saveOrderDetails(OrderDetails orderDetails);

    @Select("""
             SELECT
            	od.orderDetailId
            	, od.orderId
            	, od.productId
            	, od.memberId
            	, od.productQty
            	, p.pBrand AS brand
                , p.pName AS name
                , p.pColor AS color
                , p.pSize AS size
                , SUM(p.pPrice * od.productQty) AS totalPrice
                , COUNT(od.productId) AS countProduct
            FROM OrderDetails od
            LEFT JOIN
            	Products p ON p.pId = od.productId
            WHERE od.memberId = #{memberId} AND od.orderId = #{orderId};
            """)
    GetOrderResponse getOrderInfo(@Param("memberId") Integer memberId, @Param("orderId") Integer orderId);

    @Select("""
            SELECT COUNT(*) FROM Payment;
            """)
    boolean isInPayment(Integer orderId);

//    @Select("""
//            SELECT
//            	o.orderId
//            	, o.memberId
//            	, p.productName
//            	, od.productId
//            	, od.productQty
//            	, od.productPrice
//            	, p.paymentStatus
//            	, p.paymentDate
//            	, pf.fileName
//            FROM Orders o
//            LEFT JOIN
//            	OrderDetails od ON o.orderId = od.orderId
//            LEFT JOIN
//            	Payment p ON o.orderId = p.orderId
//            LEFT JOIN
//            	ProductFile pf ON od.productId = pf.productId
//            WHERE o.memberId = #{memberId}
//            ORDER BY p.paymentDate DESC;
//            """)
//    @ResultMap("orderResultMap")
//    List<CompleteOrderListResponse> getCompleteOrderList(Integer memberId);

    @Select("""
            SELECT
            	o.orderId
            	, o.memberId
            	, p.productName
            	, od.productId
            	, od.productQty
            	, od.productPrice
            	, p.paymentStatus
            	, p.paymentDate
            	, pf.fileName
            FROM Orders o
            LEFT JOIN
            	OrderDetails od ON o.orderId = od.orderId
            LEFT JOIN
            	Payment p ON o.orderId = p.orderId
            LEFT JOIN
            	ProductFile pf ON od.productId = pf.productId
            WHERE 
                o.memberId = #{memberId}
            AND 
                p.paymentDate BETWEEN #{startDate} AND #{endDate}
            ORDER BY 
                p.paymentDate DESC;
            """)
    @ResultMap("ordersByDateRangeResultMap")
    List<OrdersByDateRangeResponse> getOrderByDateRange(Integer memberId, String startDate, String endDate);

    @Select("""
            SELECT MAX(orderId) 
            FROM Orders
            """)
    Integer getMaxOrderId();
}


//SELECT
//        od.orderDetailId
//        , od.orderId
//        , od.productId
//        , od.memberId
//        , od.productQty
//        , p.pBrand AS brand
//        , p.pName AS name
//        , p.pColor AS color
//        , p.pSize AS size
//        , SUM(p.pPrice * od.productQty) AS totalPrice
//        , COUNT(od.productId) AS countProduct
//        FROM OrderDetails od
//        LEFT JOIN
//        Products p ON p.pId = od.productId
//        WHERE od.memberId = #{memberId} AND od.orderId = #{orderId};