package com.example.shop.controller;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.request.LoginRequest;
import com.example.shop.service.AuthService;
import com.example.shop.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    MemberService memberService;

    @Autowired
    AuthService authService;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody Map<String, Object> request) throws Exception {
        System.out.println("signup.getName() = " + request);
        ObjectMapper objectMapper = new ObjectMapper();
        Signup signup = objectMapper.convertValue(request.get("signupMember"), Signup.class);
        authService.signup(signup);

    }

    @PostMapping("/auth/login")
    public Optional<Members> login(@RequestBody LoginRequest loginRequest) throws Exception {

        Optional<Members> members = authService.login(loginRequest);
        System.out.println("loginRequest = " + loginRequest);
        return members;
    }


}
