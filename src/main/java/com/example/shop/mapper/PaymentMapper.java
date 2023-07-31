package com.example.shop.mapper;

import com.example.shop.request.payment.SavePaymentRequest;
import com.example.shop.request.payment.UpdateDeliveryStatusRequest;
import com.example.shop.response.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PaymentMapper {


    @Insert("""
            INSERT INTO Payment (
                orderId
                , productName
                , amount
                , memberId
                , invoice
                , deliveryRequest
                , email
                , name
                , phone
                , address )
            VALUES (
                #{orderId}
                , #{productName}
                , #{amount}
                , #{memberId}
                , #{invoice}
                , #{deliveryRequest}
                , #{email}
                , #{name}
                , #{phone}
                , #{address} )
            """)
    void savePayInfo(SavePaymentRequest request);

    @Select("""
            SELECT COUNT(paymentId)
            FROM Payment
            WHERE orderId = #{orderId}
            """)
    Integer countInPayment(Integer orderId);

    @Select("""
            SELECT 
                invoice
                , amount
                , orderId
            FROM Payment
            WHERE 
                memberId = #{memberId} AND  orderId = #{orderId};
            """)
    SuccessPaymentResponse getPaymentInfo(Integer memberId, Integer orderId);


    @Select("""
            SELECT 
                orderId
                , invoice
                , productName
                , paymentStatus
                , memberId
                , deliveryRequest
                , email
                , name
                , address
            FROM 
                Payment p
            WHERE 
                memberId = #{memberId}
            """)
    List<DeliveryInfoResponse> getDeliveryInfoPayment(Integer memberId);

    @Select("""
            SELECT 
                paymentId
                , orderId
                , productName
                , amount
                , paymentDate
                , invoice
                , memberId 
                , name
                , address
                , deliveryRequest
                , paymentStatus
            FROM Payment
            ORDER BY paymentDate DESC;
            """)
    List<AllPaymentListResponse> getAllPaymentList();

    @Update("""
            UPDATE 
                Payment
            SET 
                paymentStatus = #{paymentStatus}
            WHERE
                paymentId = #{paymentId} AND orderId = #{orderId}
            """)
    void updateDeliveryStatus(UpdateDeliveryStatusRequest updateDeliveryStatusRequest);

    @Select("""
            SELECT
              YEAR(paymentDate) AS year,
              MONTH(paymentDate) AS month,
              SUM(amount) AS monthlyRevenue
            FROM
              Payment
            WHERE YEAR(paymentDate) = 2023
            GROUP BY
              YEAR(paymentDate),
              MONTH(paymentDate)
            ORDER BY
              YEAR(paymentDate),
              MONTH(paymentDate);
            """)
    List<MonthlySalesAmountResponse> getMonthlySalesAmount(Integer year);

    @Select("""
            SELECT
              YEAR(paymentDate) AS year,
              SUM(amount) AS yearlyRevenue
            FROM
              Payment
            GROUP BY
              YEAR(paymentDate)
            ORDER BY
              YEAR(paymentDate);
            """)
    List<YearlySalesAmountResponse> getYearlySalesAmount();


    @Select("""
            SELECT 
                paymentId
                , orderId
                , productName
                , amount
                , paymentDate
                , invoice
                , memberId 
                , name
                , address
                , deliveryRequest
                , paymentStatus
            FROM Payment
            WHERE YEAR(paymentDate) = #{year}
            ORDER BY paymentDate DESC;
            """)
    List<AllPaymentListResponse> getSelectedYearPaymentList(Integer year);

    @Select("""
        SELECT 
            paymentId
            , orderId
            , productName
            , amount
            , paymentDate
            , invoice
            , memberId 
            , name
            , address
            , deliveryRequest
            , paymentStatus
        FROM Payment
        WHERE paymentStatus IN ('${selectedStatuses}')
        ORDER BY paymentDate DESC;
        """)
    List<AllPaymentListResponse> searchDeliveryStatus(String statusesAsString);
}
