package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductGetResponse {
    private Integer pId;
    private Integer categoryId;
    private String categoryName;
    private String pName;
    private Integer pPrice;
    private String pBrand;
    private String pDesc;
    private String pStatus;
    private Integer pStock;
    private String pSize;
    private String pColor;
    private List<String> fileName;
    private List<String> linkName;

    public ProductGetResponse(Integer pId, Integer categoryId, String categoryName, String pName, Integer pPrice, String pBrand, String pDesc, String pStatus, Integer pStock, String pSize, String pColor, List<String> fileName, List<String> linkName) {
        this.pId = pId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pBrand = pBrand;
        this.pDesc = pDesc;
        this.pStatus = pStatus;
        this.pStock = pStock;
        this.pSize = pSize;
        this.pColor = pColor;
        this.fileName = fileName;
        this.linkName = linkName;
    }
}
