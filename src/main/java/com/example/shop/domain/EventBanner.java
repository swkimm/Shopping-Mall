package com.example.shop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventBanner {

    private Integer eventBannerId;
    private String content;

    public EventBanner(Integer eventBannerId, String content) {
        this.eventBannerId = eventBannerId;
        this.content = content;
    }
}
