package com.homss.server.controller;

import com.homss.server.common.annotation.CurrentMemberId;
import com.homss.server.dto.request.SocialLoginRequest;
import com.homss.server.dto.response.SocialLoginResponse;
import com.homss.server.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SocialLoginResponse> login(@Valid @RequestBody SocialLoginRequest request) {
        SocialLoginResponse response = authService.login(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CurrentMemberId Long memberId) {
        authService.logout(memberId);
        return ResponseEntity.ok().build();
    }
}
