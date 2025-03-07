package com.example.securitySG.services;

import com.example.securitySG.dtos.LoginDto;
import com.example.securitySG.dtos.UserDto;
import com.example.securitySG.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {

    Optional<UserEntity> getDetails(Long userId);

    Map<String, String> login(LoginDto loginDto);

    UserDto createUser(UserDto userDto);
}
