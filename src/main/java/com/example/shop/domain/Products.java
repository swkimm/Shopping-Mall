package com.example.shop.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Products {

    private Integer pId;
    private Integer cId;
    private String pName;
    private Integer pPrice;
    private Integer pStock;
    private String pBrand;
    private String pDesc;
    private String pStatus;
    private String pSize;
    private String pColor;
    private LocalDateTime pRegDate;
    private List<String> fileName;

    @Builder
    public Products(Integer pId, Integer cId, String pName, Integer pPrice, Integer pStock, String pBrand, String pDesc, String pStatus, String pSize, String pColor, LocalDateTime pRegDate, List<String> fileName) {
        this.pId = pId;
        this.cId = cId;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pStock = pStock;
        this.pBrand = pBrand;
        this.pDesc = pDesc;
        this.pStatus = pStatus;
        this.pSize = pSize;
        this.pColor = pColor;
        this.pRegDate = pRegDate;
        this.fileName = fileName;
    }
}
