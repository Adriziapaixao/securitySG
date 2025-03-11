package com.example.securitySG.services;

import com.example.securitySG.controllers.dtos.LoginDto;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public interface AuthService {

    String refreshToken(String refreshToken);


    String login(LoginDto loginDto);
}
