package com.personalfinance.personal_finance_api.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personalfinance.personal_finance_api.dto.request.LoginRequest;
import com.personalfinance.personal_finance_api.dto.request.RegisterRequest;
import com.personalfinance.personal_finance_api.dto.response.LoginResponse;
import com.personalfinance.personal_finance_api.dto.response.UserResponse;
import com.personalfinance.personal_finance_api.entity.User;
import com.personalfinance.personal_finance_api.exception.BadRequestException;
import com.personalfinance.personal_finance_api.repository.UserRepository;
import com.personalfinance.personal_finance_api.service.JwtService;
import com.personalfinance.personal_finance_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

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
        user.setCreateAt(LocalDateTime.now());
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
        String refreshToken = jwtService.generateRefreshToken(email);
        return new LoginResponse(accessToken, refreshToken);
    }

}
