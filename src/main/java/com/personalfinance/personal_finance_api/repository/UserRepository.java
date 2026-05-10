package com.personalfinance.personal_finance_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.personalfinance.personal_finance_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
