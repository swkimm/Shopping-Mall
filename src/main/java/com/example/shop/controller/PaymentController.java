package com.example.shop.controller;

import com.example.shop.request.delivery.SearchDeliveryStatusRequest;
import com.example.shop.request.payment.PaymentVerificationRequest;
import com.example.shop.request.payment.SavePaymentRequest;
import com.example.shop.request.payment.UpdateDeliveryStatusRequest;
import com.example.shop.response.*;
import com.example.shop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment/savePayInfo")
    public void savePayInfo(@RequestBody SavePaymentRequest request) {
        paymentService.savePayInfo(request);

    }

    @PostMapping("/payment/isInPayment")
    public Integer isInPayment(@RequestBody Map<String, Integer> request) {
        Integer orderId = request.get("orderId");
        boolean isInPayment = paymentService.isInPayment(orderId);
        return isInPayment ? 1 : 0;
    }

    @PostMapping("/payment/getPaymentInfo")
    public SuccessPaymentResponse getPaymentInfo(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        Integer orderId = request.get("orderId");
        return paymentService.getPaymentInfo(memberId, orderId);
    }

    @PostMapping("/payment/getDeliveryInfoInPayment")
    public List<DeliveryInfoResponse> getDeliveryInfoInPayment(@RequestBody Map<String, Integer> request) {
        Integer memberId = request.get("memberId");
        Integer invoice = request.get("invoice");
//        System.out.println("invoice111111 = " + invoice);
        return paymentService.getDeliveryInfoInPayment(memberId);
    }

    @PostMapping("/payment/getAllPaymentList")
    public List<AllPaymentListResponse> getAllPaymentList() {
        return paymentService.getAllPaymentList();
    }
    @PostMapping("/payment/getSelectedYearPaymentList")
    public List<AllPaymentListResponse> getSelectedYearPaymentList(@RequestBody Map<String, Integer> request) {
        Integer year = request.get("year");
        return paymentService.getSelectedYearPaymentList(year);
    }

    @PostMapping("/payment/searchDeliveryStatus")
    public List<AllPaymentListResponse> searchDeliveryStatus(@RequestBody SearchDeliveryStatusRequest request) {
        List<String> searchDeliveryStatus = request.getSelectedStatuses();
        System.out.println("searchDeliveryStatus = " + searchDeliveryStatus);
        return paymentService.searchDeliveryStatus(searchDeliveryStatus);
    }

    @PostMapping("/payment/updateDeliveryStatus")
    public void updateDeliveryStatus(@RequestBody UpdateDeliveryStatusRequest updateDeliveryStatusRequest) {
        System.out.println("updateDeliveryStatusRequest = " + updateDeliveryStatusRequest);
        paymentService.updateDeliveryStatus(updateDeliveryStatusRequest);
    }

    @PostMapping("/payment/getMonthlySalesAmount")
    public List<MonthlySalesAmountResponse> getMonthlySalesAmount(@RequestBody Map<String, Integer> request) {
        Integer year = request.get("year");
        return paymentService.getMonthlySalesAmount(year);
    }

    @PostMapping("/payment/getYearlySalesAmount")
    public List<YearlySalesAmountResponse> getYearlySalesAmount() {
        return paymentService.getYearlySalesAmount();
    }


    @PostMapping("/verifyIamport/{impUid}")
    public PaymentVerificationResponse verifyIamportPayment(
            @PathVariable String impUid,
            @RequestBody PaymentVerificationRequest request
    ) {
        // Here, you can perform your payment verification logic
        // For simplicity, we'll just compare the paid_amount from the request with a fixed amount
        // todo: 결제 총액 계산해서 금액 넣어줘야함
        int fixedAmount = 5200; // Set your expected amount here

        PaymentVerificationResponse response = new PaymentVerificationResponse();
        response.setImpUid(impUid);
        response.setPaidAmount(request.getPaidAmount());

        if (request.getPaidAmount() == fixedAmount) {
            response.setSuccess(true);
        } else {
            response.setSuccess(false);
        }

        return response;
    }
}
