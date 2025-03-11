package com.example.securitySG.services;

import com.example.securitySG.infra.config.JwtTokenProvider;

import com.example.securitySG.models.RefreshTokenEntity;
import com.example.securitySG.models.UserEntity;
import com.example.securitySG.repostories.RefreshTokenRepository;
import com.example.securitySG.repostories.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String CLAIM_DEPARTMENT = "department";
    private static final String CLAIM_SUB = "sub";
    private static final Integer REFRESH_TOKEN_EXPIRATION_MS = 86400000;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    public String getCurrentJwt() {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public String getDepartment() {
        String token = getCurrentJwt();
        if (token != null) {
            Claims claims = jwtTokenProvider.getClaimsFromToken(token);
            return claims.get(CLAIM_DEPARTMENT, String.class);
        }
        return null;
    }

    public String getUsername() {
        String token = getCurrentJwt();
        if (token != null) {
            return jwtTokenProvider.getUsername(token);
        }
        return null;
    }

    public Object createRefreshToken(String username) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setToken(jwtTokenProvider.generateTokenFromUser(new UserEntity(username)));
        refreshToken.setExpiryDate(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_MS).toInstant());
        refreshToken.setUsername(username);
        return refreshTokenRepository.save(refreshToken);
    }


    public boolean verifyExpiration(RefreshTokenEntity refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(new Date().toInstant())) {
            refreshTokenRepository.delete(refreshToken);
            return false;
        }
        return true;
    }
}

