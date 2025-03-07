package com.example.securitySG.services;

import com.example.securitySG.dtos.LoginDto;
import com.example.securitySG.dtos.UserDto;
import com.example.securitySG.models.UserEntity;
import com.example.securitySG.repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userEntrada) {
        String passwordEncoder = bCryptPasswordEncoder.encode(userEntrada.getPassword());
        UserEntity userParaSalvar = new UserEntity();
            userParaSalvar.setName(userEntrada.getName());
            userParaSalvar.setUsername(userEntrada.getUsername());
            userParaSalvar.setEmail(userEntrada.getEmail());
            userParaSalvar.setPassword(passwordEncoder);

        UserEntity userSalvo = userRepository.save(userParaSalvar);
        UserDto usuarioParaRetorno = new UserDto();
            usuarioParaRetorno.setId(userSalvo.getId());
            usuarioParaRetorno.setName(userSalvo.getName());
            usuarioParaRetorno.setUsername(userSalvo.getUsername());
            usuarioParaRetorno.setEmail(userSalvo.getEmail());

        return usuarioParaRetorno;
    }

    @Override
    public Optional<UserEntity> getDetails(Long userId){
        return userRepository.findById(userId);
    }

    @Override
    public Map<String, String> login(LoginDto loginDto) {
        return Map.of("username", loginDto.getUsername(),
                "password", loginDto.getPassword()
        );
    }
}

