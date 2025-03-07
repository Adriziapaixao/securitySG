package com.example.securitySG.infra.config;

import com.example.securitySG.models.UserEntity;

public interface TokenProvider {
    String generateToken(UserEntity user);
    boolean validateRefreshToken(String refreshToken);
    String refreshAccessToken(String refreshToken);
}
