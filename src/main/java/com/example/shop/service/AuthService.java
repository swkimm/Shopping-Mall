package com.example.shop.service;


import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.mapper.MemberMapper;
import com.example.shop.request.member.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberMapper memberMapper;

    public void signup(Signup signup) throws Exception {
        Optional<Members> membersOptional = memberMapper.findByEmail(signup.getEmail());
        if (membersOptional.isPresent()) {
            throw new Exception();
        }


        var members = Members.builder()
                .name((signup.getName()))
                .phone((signup.getPhone()))
                .nickName((signup.getNickName()))
                .email(signup.getEmail())
                .pwd(signup.getPwd())
                .address(signup.getAddress())
                .build();
        memberMapper.save(members);

    }

    public Optional<Members> login(LoginRequest loginRequest) throws Exception {
        Optional<Members> members = memberMapper.findByEmail(loginRequest.getEmail());
        if (members.isPresent()) {
            Members member = members.get();
            if (member.getPwd().equals(loginRequest.getPwd())) {
                System.out.println("로그인 성공");
                return members;
            } else {
                System.out.println("비밀번호 오류");
                return Optional.empty();
            }
        } else {
            System.out.println("아이디 오류");
            return Optional.empty();
        }
    }
}
