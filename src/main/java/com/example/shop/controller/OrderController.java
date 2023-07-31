package com.example.shop.controller;

import com.example.shop.domain.OrderDetails;
import com.example.shop.request.order.SaveOrderDetailRequest;
import com.example.shop.response.CompleteOrderListResponse;
import com.example.shop.response.GetOrderResponse;
import com.example.shop.response.OrderMemberResponse;
import com.example.shop.response.OrdersByDateRangeResponse;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order/saveOrder")
    public Integer saveOrder(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        orderService.saveOrder(memberId);
        return orderService.getOrderId(memberId);
    }

    @PostMapping("/order/getOrderMemberInfo")
    public OrderMemberResponse getOrderMemberInfo(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");

        return orderService.getOrderMemberInfo(memberId);
    }

    @PostMapping("/order/saveOrderDetails")
    public void saveOrderDetails(@RequestBody Map<String, List<SaveOrderDetailRequest>> requestList) {
        // orderDetailsList에서 orderId를 키로 사용하여 주문 세부 정보를 가져옴
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        for (String orderId : requestList.keySet()) {
            List<SaveOrderDetailRequest> details = requestList.get(orderId);
            for (SaveOrderDetailRequest saveOrderDetailRequest : details) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderId(saveOrderDetailRequest.getOrderId());
//                orderDetails.setCartId(saveOrderDetail.getCartId());
                orderDetails.setMemberId(saveOrderDetailRequest.getMemberId());
                orderDetails.setProductId(saveOrderDetailRequest.getProductId());
                orderDetails.setProductQty(saveOrderDetailRequest.getProductQty());
                orderDetails.setProductPrice(saveOrderDetailRequest.getProductPrice());

                orderDetailsList.add(orderDetails);
            }
        }
        System.out.println("orderDetailsList = " + orderDetailsList);

        // orderDetailsList를 OrderService의 saveOrderDetails 메서드에 전달하여 저장
        orderService.saveOrderDetails(orderDetailsList);
    }

    @PostMapping("/order/getOrderId")
    public Integer getOrderId(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        return orderService.getOrderId(memberId);
    }

    @PostMapping("/order/getOrderInfo")
    public GetOrderResponse getOrderInfo(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        Integer orderId = request.get("orderId");
        System.out.println("orderId123123 = " + orderId);
        return orderService.getOrderInfo(memberId, orderId);
    }

//    @PostMapping("/order/getCompleteOrderList")
//    public List<CompleteOrderListResponse> getCompleteOrderList(@RequestBody Map<String, Integer> request) {
//        Integer memberId = request.get("memberId");
//        return orderService.getCompleteOrderList(memberId);
//    }

    @PostMapping("/order/getOrdersByDateRange")
    public List<OrdersByDateRangeResponse> getOrdersByDateRange(@RequestBody Map<String, String> request) {
        Integer memberId = Integer.parseInt(request.get("memberId"));
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");

        return orderService.getOrdersByDateRange(memberId, startDate, endDate);
    }

}

