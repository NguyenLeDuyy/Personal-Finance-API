package com.personalfinance.personal_finance_api.service;

import com.personalfinance.personal_finance_api.dto.request.RegisterRequest;
import com.personalfinance.personal_finance_api.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest request);
}
