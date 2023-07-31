package com.example.shop.service;

import com.example.shop.mapper.PaymentMapper;
import com.example.shop.request.payment.SavePaymentRequest;
import com.example.shop.request.payment.UpdateDeliveryStatusRequest;
import com.example.shop.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class PaymentService {

    @Autowired
    PaymentMapper paymentMapper;

    public void savePayInfo(SavePaymentRequest request) {
        if (request.getDeliveryRequest() == null) {
            request.setDeliveryRequest("문 앞");
        }

        // 중복되지 않는 4자리 난수 생성
        String randomCode = generateUniqueRandomCode();

        // Invoice에 난수 추가
        long invoiceNumber = convertToLongFormat(request.getPaymentDate()) + Long.parseLong(randomCode);
        request.setInvoice((int) invoiceNumber);
        System.out.println("request = " + request.getPaymentDate());

        paymentMapper.savePayInfo(request);
    }

    // 중복되지 않는 4자리 난수 생성 메서드
    private String generateUniqueRandomCode() {
        Set<String> codes = new HashSet<>();
        Random random = new Random();

        while (codes.size() < 1) { // 한 개의 난수만 생성
            int randomNumber = random.nextInt(10000); // 0부터 9999 사이의 난수 생성
            String paddedCode = String.format("%04d", randomNumber); // 4자리로 패딩 처리
            codes.add(paddedCode);
        }

        return codes.iterator().next(); // 유일한 난수 반환
    }

    // 입력된 날짜를 숫자로 변환하는 메서드
    private long convertToLongFormat(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = localDateTime.format(formatter);
        return Long.parseLong(formattedDate);
    }

    public boolean isInPayment(Integer orderId) {
        System.out.println("isInPayment service" + orderId);
        Integer count = paymentMapper.countInPayment(orderId);
        return count > 0;
    }

    public SuccessPaymentResponse getPaymentInfo(Integer memberId, Integer orderId) {

        return paymentMapper.getPaymentInfo(memberId, orderId);
    }

    public List<DeliveryInfoResponse> getDeliveryInfoInPayment(Integer memberId) {
        return paymentMapper.getDeliveryInfoPayment(memberId);
    }

    public List<AllPaymentListResponse> getAllPaymentList() {
        return paymentMapper.getAllPaymentList();
    }

    public List<AllPaymentListResponse> getSelectedYearPaymentList(Integer year) {
        return paymentMapper.getSelectedYearPaymentList(year);
    }

    // Method to search delivery status based on selected statuses
    public List<AllPaymentListResponse> searchDeliveryStatus(List<String> selectedStatuses) {
        // Convert the list of selectedStatuses to a comma-separated string
        String statusesAsString = String.join(",", selectedStatuses);

        // Fetch the filtered payment data using the searchDeliveryStatus method
        return paymentMapper.searchDeliveryStatus(statusesAsString);
    }

    public void updateDeliveryStatus(UpdateDeliveryStatusRequest updateDeliveryStatusRequest) {
        paymentMapper.updateDeliveryStatus(updateDeliveryStatusRequest);
    }

    public List<MonthlySalesAmountResponse> getMonthlySalesAmount(Integer year) {
        return paymentMapper.getMonthlySalesAmount(year);
    }

    public List<YearlySalesAmountResponse> getYearlySalesAmount() {
        return paymentMapper.getYearlySalesAmount();
    }


}
