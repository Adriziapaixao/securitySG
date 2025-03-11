package com.example.securitySG.services;

import com.example.securitySG.models.RefreshTokenEntity;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String getCurrentJwt();
    String getDepartment();
    String getUsername();
    Object createRefreshToken(String username);
    boolean verifyExpiration(RefreshTokenEntity refreshToken);
}

