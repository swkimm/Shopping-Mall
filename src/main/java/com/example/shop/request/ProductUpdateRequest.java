package com.example.shop.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductUpdateRequest {

    private Integer pId;
    private Integer categoryId;
    private String categoryName;
    private String pStatus;
    private String pName;
    private Integer pPrice;
    private String pBrand;
    private String pDesc;
    private Integer pStock;
    private String pSize;
    private String pColor;
    private List<String> fileName;
    private List<String> linkName;


    public ProductUpdateRequest(Integer pId, Integer categoryId, String categoryName, String pStatus, String pName, Integer pPrice, String pBrand, String pDesc, Integer pStock, String pSize, String pColor, List<String> fileName, List<String> linkName) {
        this.pId = pId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.pStatus = pStatus;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pBrand = pBrand;
        this.pDesc = pDesc;
        this.pStock = pStock;
        this.pSize = pSize;
        this.pColor = pColor;
        this.fileName = fileName;
        this.linkName = linkName;
    }
}
