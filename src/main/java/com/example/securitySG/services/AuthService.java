package com.example.securitySG.services;

import org.springframework.stereotype.Service;


@Service
public interface AuthService {

    String refreshToken(String refreshToken);


}
