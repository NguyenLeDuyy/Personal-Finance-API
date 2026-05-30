package com.taskmanagement.task_management_api.service;

public interface JwtService {
    String generateAccessToken(String email);

    String generateRefreshToken(String email);

    String extractEmail(String token);

    boolean isTokenValid(String token);
}
