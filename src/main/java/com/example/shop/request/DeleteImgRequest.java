package com.example.shop.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteImgRequest {

    private Integer pId;
    private String fileName;

    public DeleteImgRequest(Integer pId, String fileName) {
        this.pId = pId;
        this.fileName = fileName;
    }
}
