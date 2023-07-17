package com.example.shop.controller;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Map;
import java.util.Objects;


@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/member/getMemberInfo")
    public Members getMemberInfo() {
        Members members = memberService.getMemberInfo();
        return members;
    }

    @PostMapping("/member/duplCheckNickname")
    public Integer duplCheckNickName(@RequestBody Map<String, String> request) {
        String nickName = request.get("nickName");
        System.out.println("nickName = " + nickName);
        return memberService.duplCheckNickName(nickName);
    }

    @PostMapping("/member/duplCheckEmail")
    public Integer dupleCheckEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("email = " + email);
        return memberService.duplCheckEmail(email);
    }



}