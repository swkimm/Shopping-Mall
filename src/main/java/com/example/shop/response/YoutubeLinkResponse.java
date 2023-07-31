package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YoutubeLinkResponse {

    private Integer linkId;
    private Integer productId;
    private String linkName;

    public YoutubeLinkResponse(Integer linkId, Integer productId, String linkName) {
        this.linkId = linkId;
        this.productId = productId;
        this.linkName = linkName;
    }
}
