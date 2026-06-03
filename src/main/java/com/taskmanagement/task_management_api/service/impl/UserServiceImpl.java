package com.taskmanagement.task_management_api.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanagement.task_management_api.dto.RefreshTokenDetails;
import com.taskmanagement.task_management_api.dto.request.LoginRequest;
import com.taskmanagement.task_management_api.dto.request.RefreshTokenRequest;
import com.taskmanagement.task_management_api.dto.request.RegisterRequest;
import com.taskmanagement.task_management_api.dto.response.LoginResponse;
import com.taskmanagement.task_management_api.dto.response.UserResponse;
import com.taskmanagement.task_management_api.entity.RefreshToken;
import com.taskmanagement.task_management_api.entity.User;
import com.taskmanagement.task_management_api.exception.BadRequestException;
import com.taskmanagement.task_management_api.repository.RefreshTokenRepository;
import com.taskmanagement.task_management_api.repository.UserRepository;
import com.taskmanagement.task_management_api.service.JwtService;
import com.taskmanagement.task_management_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại!");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullName());
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFullname());
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new BadRequestException("Sai mật khẩu hoặc tài khoản!");
        }

        String hashedPassword = user.get().getPassword();
        if (!passwordEncoder.matches(request.getPassword(), hashedPassword)) {
            throw new BadRequestException("Sai mật khẩu hoặc tài khoản!");
        }

        String email = user.get().getEmail();
        String accessToken = jwtService.generateAccessToken(email);
        RefreshTokenDetails refreshToken = jwtService.generateRefreshToken(email);

        RefreshToken dbToken = new RefreshToken();
        dbToken.setId(refreshToken.getJti());
        dbToken.setRevoked(false);
        dbToken.setExpiryDate(refreshToken.getExpiryDate());
        dbToken.setUser(user.get());
        refreshTokenRepository.save(dbToken);

        return new LoginResponse(accessToken, refreshToken.getToken());
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {

        String jti = jwtService.extractJti(request.getRefreshToken());
        RefreshToken dbToken = refreshTokenRepository.findById(jti)
                .orElseThrow(() -> new BadRequestException("Refresh Token không tồn tại!"));

        if (Boolean.TRUE.equals(dbToken.getRevoked()) || dbToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Refresh Token đã hết hạn hoặc đã bị vô hiệu hóa!");
        }

        String email = dbToken.getUser().getEmail();
        String newAccessToken = jwtService.generateAccessToken(email);
        RefreshTokenDetails newRefreshToken = jwtService.generateRefreshToken(email);

        RefreshToken newDbToken = new RefreshToken();
        newDbToken.setId(newRefreshToken.getJti());
        newDbToken.setRevoked(false);
        newDbToken.setExpiryDate(newRefreshToken.getExpiryDate());
        newDbToken.setUser(dbToken.getUser());
        refreshTokenRepository.save(newDbToken);

        dbToken.setRevoked(true);
        refreshTokenRepository.save(dbToken);

        return new LoginResponse(newAccessToken, newRefreshToken.getToken());
    }

    @Override
    public void logout(String refreshToken) {
        String jti = jwtService.extractJti(refreshToken);
        RefreshToken dbToken = refreshTokenRepository.findById(jti)
                .orElseThrow(() -> new BadRequestException("Refresh token không tồn tại!"));

        dbToken.setRevoked(true);
        refreshTokenRepository.save(dbToken);
    }

}
