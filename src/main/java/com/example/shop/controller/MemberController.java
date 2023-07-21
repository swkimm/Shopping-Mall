package com.example.shop.controller;

import com.example.shop.domain.Members;
import com.example.shop.request.MemberUpdateRequest;
import com.example.shop.response.MemberListResponse;
import com.example.shop.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/member/getMemberInfo")
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

    @PostMapping("/member/getMemberProfile")
    public Members getMemberProfile(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        System.out.println("email = " + email);
        return memberService.getMemberProfile(email);
    }

    @PostMapping("/member/updateProfile")
    public void updateProfile(@RequestBody Map<String, Object> request) {
        ObjectMapper objectMapper = new ObjectMapper();
        MemberUpdateRequest update = objectMapper.convertValue(request.get("updateValue"), MemberUpdateRequest.class);
        String email = update.getEmail();
        memberService.updateProfile(update, email);
    }

    @PostMapping("/admin/getMemberList")
    public List<MemberListResponse> getMemberList() {
        Integer authority = 1;
        return memberService.getMemberList(authority);
    }

    @PostMapping("/admin/deleteMember")
    public String deleteMember(@RequestBody Map<String, Object> request) {
        System.out.println("request = " + request);
        String email = (String) request.get("email");
        System.out.println("email = " + email);

        if (memberService.deleteMember(email) == 1) {
            return "회원을 삭제하였습니다.";
        } else {
            return "회원을 삭제하지 못했습니다.";
        }
    }

}