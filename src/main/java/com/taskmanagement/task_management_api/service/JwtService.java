package com.taskmanagement.task_management_api.service;

import com.taskmanagement.task_management_api.dto.RefreshTokenDetails;

public interface JwtService {
    String generateAccessToken(String email);

    RefreshTokenDetails generateRefreshToken(String email);

    String extractEmail(String token);

    String extractJti(String token);

    boolean isTokenValid(String token);
}
