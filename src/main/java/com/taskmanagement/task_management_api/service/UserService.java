package com.taskmanagement.task_management_api.service;

import com.taskmanagement.task_management_api.dto.request.LoginRequest;
import com.taskmanagement.task_management_api.dto.request.RefreshTokenRequest;
import com.taskmanagement.task_management_api.dto.request.RegisterRequest;
import com.taskmanagement.task_management_api.dto.response.LoginResponse;
import com.taskmanagement.task_management_api.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void logout(String refreshToken);
}
