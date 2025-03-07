package infra.config;

import models.UserEntity;

public interface TokenProvider {
    String generateToken(UserEntity user);
    boolean validateRefreshToken(String refreshToken);
    String refreshAccessToken(String refreshToken);
}
