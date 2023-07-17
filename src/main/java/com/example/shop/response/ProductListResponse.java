package com.example.shop.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductListResponse {

    private Integer pId;
    private Integer categoryId;
    private String categoryName;
    private String pName;
    private Integer pPrice;
    private String pBrand;
    private String pStatus;
    private Integer pStock;
    private String pSize;
    private String pColor;
    private List<String> fileName;

    public ProductListResponse(Integer pId, Integer categoryId, String categoryName, String pName, Integer pPrice, String pBrand, String pStatus, Integer pStock, String pSize, String pColor, List<String> fileName) {
        this.pId = pId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pBrand = pBrand;
        this.pStatus = pStatus;
        this.pStock = pStock;
        this.pSize = pSize;
        this.pColor = pColor;
        this.fileName = fileName;
    }
}
