package com.example.securitySG.repostories;


import com.example.securitySG.models.RefreshTokenEntity;
import com.example.securitySG.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUser(UserEntity user);

}
