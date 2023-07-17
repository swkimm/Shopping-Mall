package com.example.shop.service;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberService {


    @Autowired
    MemberMapper memberMapper;


    public Members getMemberInfo() {
        return memberMapper.findMemberByEmail();
    }

    public Integer duplCheckNickName(String nickName) {
        Integer result = memberMapper.duplCheckNickName(nickName);
        System.out.println("result = " + result);
        return result;
    }

    public Integer duplCheckEmail(String email) {
        Integer checkEmail = memberMapper.duplCheckEmail(email);
        System.out.println("checkEmail = " + checkEmail);
        return checkEmail;
    }
}
