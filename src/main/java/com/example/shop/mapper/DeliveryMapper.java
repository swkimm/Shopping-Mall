package com.example.shop.mapper;

import com.example.shop.domain.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    @Select("""
            SELECT * FROM Delivery;
            """)
    List<Delivery> getDeliveryStatusList();
}
