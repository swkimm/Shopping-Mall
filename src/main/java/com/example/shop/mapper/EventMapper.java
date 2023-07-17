package com.example.shop.mapper;

import com.example.shop.domain.EventBanner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("""
            SELECT * FROM EventBanner;
            """)
    List<EventBanner> getEventBanners();
}
