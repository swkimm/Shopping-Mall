package com.example.shop.controller;

import com.example.shop.domain.Members;
import com.example.shop.domain.Signup;
import com.example.shop.request.LoginRequest;
import com.example.shop.service.AuthService;
import com.example.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    MemberService memberService;

    @Autowired
    AuthService authService;

    @PostMapping("/auth/signup")
    public void signup(Signup signup) throws Exception {

        authService.signup(signup);

    }

    @PostMapping("/auth/login")
    public Optional<Members> login(@RequestBody LoginRequest loginRequest) throws Exception {

        Optional<Members> members = authService.login(loginRequest);
        System.out.println("loginRequest = " + loginRequest);
        return members;
    }


}
