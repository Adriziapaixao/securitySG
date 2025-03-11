package com.example.securitySG.services;

import com.example.securitySG.controllers.dtos.RegisterUserDto;
import com.example.securitySG.controllers.dtos.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

        RegisterUserDto createUser(RegisterUserDto registerUserDto);


    UserResponseDto getDetails();
}
