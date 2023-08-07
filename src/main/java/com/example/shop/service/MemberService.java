package com.example.shop.service;

import com.example.shop.domain.Members;
import com.example.shop.mapper.MemberMapper;
import com.example.shop.request.member.MemberUpdateRequest;
import com.example.shop.response.MemberListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Members getMemberProfile(String email) {
        return memberMapper.getMemberProfile(email);
    }

    public void updateProfile(MemberUpdateRequest update, String email) {
        if (update.getAddress().equals("")) {
            String address = memberMapper.getAddress(email);
            memberMapper.updateProfileAddress(update, email, address);
        } else {
            memberMapper.updateProfile(update, email);
        }
    }

    public List<MemberListResponse> getMemberList(Integer authority) {
        return memberMapper.getMemberList(authority);
    }

    public Integer deleteMember(String email) {
        if (memberMapper.deleteMember(email) == 1) {
            return 1;
        } else {
            return 0;
        }
    }
}
