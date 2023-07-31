package com.example.shop.request.member;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {

    private String name;
    private String phone;
    private String nickName;
    private String email;
    private String address;

    public MemberUpdateRequest(String name, String phone, String nickName, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.nickName = nickName;
        this.email = email;
        this.address = address;
    }
}
