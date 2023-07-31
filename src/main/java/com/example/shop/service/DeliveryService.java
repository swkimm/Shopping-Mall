package com.example.shop.service;

import com.example.shop.domain.Delivery;
import com.example.shop.mapper.DeliveryMapper;
import com.example.shop.response.AllPaymentListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeliveryService {

    @Autowired
    DeliveryMapper deliveryMapper;

    public List<Delivery> getDeliveryStatusList() {
        return deliveryMapper.getDeliveryStatusList();
    }


}
