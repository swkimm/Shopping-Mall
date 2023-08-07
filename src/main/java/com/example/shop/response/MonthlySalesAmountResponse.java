package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MonthlySalesAmountResponse {

    private Integer year;
    private Integer month;
    private Integer monthlyRevenue;

    public MonthlySalesAmountResponse(Integer year, Integer month, Integer monthlyRevenue) {
        this.year = year;
        this.month = month;
        this.monthlyRevenue = monthlyRevenue;
    }
}
