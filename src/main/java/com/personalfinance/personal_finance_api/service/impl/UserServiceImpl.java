package com.personalfinance.personal_finance_api.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.personalfinance.personal_finance_api.dto.request.RegisterRequest;
import com.personalfinance.personal_finance_api.dto.response.UserResponse;
import com.personalfinance.personal_finance_api.entity.User;
import com.personalfinance.personal_finance_api.exception.BadRequestException;
import com.personalfinance.personal_finance_api.repository.UserRepository;
import com.personalfinance.personal_finance_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

}
