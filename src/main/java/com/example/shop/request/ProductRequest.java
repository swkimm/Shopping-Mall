package com.example.shop.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductRequest {

    private Integer cId;
    private String pName;
    private Integer pPrice;
    private Integer pStock;
    private String pBrand;
    private String pDesc;
    private String pSize;
    private String pColor;
    private List<String> fileName;

    @Builder
    public ProductRequest(Integer cId, String pName, Integer pPrice, Integer pStock, String pBrand, String pDesc, String pSize, String pColor, List<String> fileName) {
        this.cId = cId;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pStock = pStock;
        this.pBrand = pBrand;
        this.pDesc = pDesc;
        this.pSize = pSize;
        this.pColor = pColor;
        this.fileName = fileName;
    }
}
