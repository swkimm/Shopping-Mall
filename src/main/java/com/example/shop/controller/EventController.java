package com.example.shop.controller;

import com.example.shop.domain.EventBanner;
import com.example.shop.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping("/event/getEventBanners")
    public List<EventBanner> getEventBanners() {
        List<EventBanner> eventBanners = eventService.getEventBanners();
        return eventBanners;
    }
}
