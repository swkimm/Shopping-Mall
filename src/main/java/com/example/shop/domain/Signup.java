package com.example.shop.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Signup {

    private String name;
    private String phone;
    private String nickName;
    private String email;
    private String pwd;
    private String address;

    @Builder
    public Signup(String name, String phone, String nickName, String email, String pwd, String address) {
        this.name = name;
        this.phone = phone;
        this.nickName = nickName;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
    }
}
