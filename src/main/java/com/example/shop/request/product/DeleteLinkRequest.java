package com.example.shop.request.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteLinkRequest {

    private Integer pId;
    private String linkName;

    public DeleteLinkRequest(Integer pId, String linkName) {
        this.pId = pId;
        this.linkName = linkName;
    }
}
