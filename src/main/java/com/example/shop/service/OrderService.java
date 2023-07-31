package com.example.shop.service;

import com.example.shop.domain.OrderDetails;
import com.example.shop.mapper.CartMapper;
import com.example.shop.mapper.OrderMapper;
import com.example.shop.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    CartMapper cartMapper;

    public OrderMemberResponse getOrderMemberInfo(Integer memberId) {
        return orderMapper.getOrderMemberInfo(memberId);
    }

    public Integer saveOrder(Integer memberId) {
        orderMapper.saveOrder(memberId);

        // orderId 리턴 받아야됨
        return orderMapper.getOrderId(memberId);
        // memberId의 cart 정보 조회하여 orderId로 OrderDetails에 값 저장
    }

    public void saveOrderDetails(List<OrderDetails> orderDetailsList) {
        // 주문 세부 정보를 데이터베이스에 저장하는 로직
        // 여기서는 간단히 주문 세부 정보를 출력해봅니다.
        for (OrderDetails orderDetails : orderDetailsList) {
            System.out.println("Saved OrderDetails: " + orderDetails.toString());
            orderMapper.saveOrderDetails(orderDetails);
        }
        // 실제로는 데이터베이스에 저장하는 로직 등을 추가하시면 됩니다.
    }

    public Integer getOrderId(Integer memberId) {
        return orderMapper.getOrderId(memberId);
    }

    public GetOrderResponse getOrderInfo(Integer memberId, Integer orderId) {
        return orderMapper.getOrderInfo(memberId, orderId);
    }


//    public List<CompleteOrderListResponse> getCompleteOrderList(Integer memberId) {
//        return orderMapper.getCompleteOrderList(memberId);
//    }

    public List<OrdersByDateRangeResponse> getOrdersByDateRange(Integer memberId, String startDate, String endDate) {
        // DateTimeFormatter를 사용하여 문자열을 LocalDateTime 객체로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        // LocalDateTime에 9시간을 더한 후, 다시 문자열로 변환하여 사용
        LocalDateTime newStartDateTime = startDateTime.plusHours(9);
        LocalDateTime newEndDateTime = endDateTime.plusHours(9);

        // 필요한 경우 DateTimeFormatter를 사용하여 LocalDateTime을 문자열로 변환
        String newStartDateString = newStartDateTime.format(formatter);
        String newEndDateString = newEndDateTime.format(formatter);

        return orderMapper.getOrderByDateRange(memberId, newStartDateString, newEndDateString);
    }
}
