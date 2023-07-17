package com.example.shop.service;


import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.mapper.MemberMapper;
import com.example.shop.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .email(signup.getEmail())
                .pwd(signup.getPassword())
                .name(signup.getName())
                .build();
        memberMapper.save(members);

    }

    public Optional<Members> login(LoginRequest loginRequest) throws Exception {

        Optional<Members> members = memberMapper.findByEmail(loginRequest.getEmail());
//        if (loginRequest.getEmail() == members.get().getEmail()) {
//            // Reqeust로 DB에 저장된 Members 조회
//            System.out.println("로그인 성공");
//            return members;
//
//        } else {
//            System.out.println("로그인 오류");
//            return Optional.empty();
//        }
            return members;

    }
}
