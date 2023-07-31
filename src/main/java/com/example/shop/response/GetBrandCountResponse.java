package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBrandCountResponse {

    private String pBrand;
    private Integer brandCount;

    public GetBrandCountResponse(String pBrand, Integer brandCount) {
        this.pBrand = pBrand;
        this.brandCount = brandCount;
    }
}
