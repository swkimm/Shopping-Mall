package com.example.shop.service;

import com.example.shop.domain.EventBanner;
import com.example.shop.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventService {

    @Autowired
    EventMapper eventMapper;

    public List<EventBanner> getEventBanners() {
        System.out.println("eventMapper = " + eventMapper);
        return eventMapper.getEventBanners();
    }
}
