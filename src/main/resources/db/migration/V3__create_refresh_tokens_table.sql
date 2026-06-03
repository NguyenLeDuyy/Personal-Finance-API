CREATE TABLE refresh_tokens (
    id VARCHAR(255) PRIMARY KEY, -- Cột khóa chính lưu jti (UUID)
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    revoked BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);