package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetColorCountResponse {

    private String pColor;
    private Integer colorCount;

    public GetColorCountResponse(String pColor, Integer colorCount) {
        this.pColor = pColor;
        this.colorCount = colorCount;
    }
}
