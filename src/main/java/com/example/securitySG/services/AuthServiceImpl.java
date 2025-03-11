package com.example.securitySG.services;

import com.example.securitySG.controllers.dtos.LoginDto;

import com.example.securitySG.infra.config.JwtTokenProvider;
import com.example.securitySG.repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;

    }

    public String login(LoginDto loginDto) {
        // 01 - AuthenticationManager is used to authenticate the user
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Generate the token based on username and secret key
        String token = jwtTokenProvider.generateToken(authentication);

        // 04 - Return the token to controller
        return token;
    }


    private String authenticateUser() {
        // Obtém o objeto Authentication do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Gera o token JWT
        return jwtTokenProvider.generateToken(authentication);
    }

    public String refreshToken(String refreshToken) {
        // Implementação de refresh token (aqui apenas retorna o mesmo token como exemplo)
        return refreshToken;
    }


}
