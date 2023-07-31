package com.example.shop.controller;

import com.example.shop.domain.Delivery;
import com.example.shop.request.delivery.SearchDeliveryStatusRequest;
import com.example.shop.response.AllPaymentListResponse;
import com.example.shop.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @PostMapping("/delivery/getDeliveryStatusList")
    public List<Delivery> getDeliveryStatusList() {
        return deliveryService.getDeliveryStatusList();
    }



}
