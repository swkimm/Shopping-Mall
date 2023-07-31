package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YearlySalesAmountResponse {

    private Integer year;
    private Integer yearlyRevenue;

    public YearlySalesAmountResponse(Integer year, Integer yearlyRevenue) {
        this.year = year;
        this.yearlyRevenue = yearlyRevenue;
    }
}
