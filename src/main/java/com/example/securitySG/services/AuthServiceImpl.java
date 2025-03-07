package com.example.securitySG.services;

import com.example.securitySG.dtos.LoginDto;

import com.example.securitySG.infra.config.JwtTokenProvider;
import com.example.securitySG.models.UserEntity;
import com.example.securitySG.repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Map<String, String> login(LoginDto loginDto) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(loginDto.getUsername());

        // Validação de entrada
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPassword())) {

                //retorna objeto mensagem
                return Map.of("mensagem:", "usuario logado");
            }
            return Map.of("mensagem:", "senha invalida");
        }
        return Map.of("mensagem:", "userName invalido");
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

    public ResponseEntity<?> login(ResponseEntity<?> login) {
        // Exemplo de metodo ResponseEntity
        return login;
    }


}
