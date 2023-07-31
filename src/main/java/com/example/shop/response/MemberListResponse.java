package com.example.shop.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberListResponse {

    private Integer memberId;
    private String name;
    private String nickName;
    private String email; // 로그인 아이디
    private String pwd; // 로그인 비밀번호
    private String address ;
    private String phone;
    private LocalDateTime regDate;
    private Integer authority;

    public MemberListResponse(Integer memberId, String name, String nickName, String email, String pwd, String address, String phone, LocalDateTime regDate, Integer authority) {
        this.memberId = memberId;
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.pwd = pwd;
        this.address = address;
        this.phone = phone;
        this.regDate = regDate;
        this.authority = authority;
    }
}
