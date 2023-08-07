package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderMemberResponse {

    private Integer memberId;
    private String name;
    private String email;
    private String phone;
    private String address;

    public OrderMemberResponse(Integer memberId, String name, String email, String phone, String address) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
