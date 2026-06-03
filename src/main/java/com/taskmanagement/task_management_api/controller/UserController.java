package com.taskmanagement.task_management_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.task_management_api.dto.request.LoginRequest;
import com.taskmanagement.task_management_api.dto.request.RefreshTokenRequest;
import com.taskmanagement.task_management_api.dto.request.RegisterRequest;
import com.taskmanagement.task_management_api.dto.response.LoginResponse;
import com.taskmanagement.task_management_api.dto.response.UserResponse;
import com.taskmanagement.task_management_api.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse createdUser = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginedUser = userService.login(request);
        return ResponseEntity.ok(loginedUser);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        LoginResponse response = userService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        userService.logout(refreshToken.getRefreshToken());
        return ResponseEntity.ok("Đăng xuất thành công!");
    }

}