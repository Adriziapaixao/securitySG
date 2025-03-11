package com.example.securitySG.services;

import com.example.securitySG.controllers.dtos.RegisterUserDto;
import com.example.securitySG.controllers.dtos.UserResponseDto;
import com.example.securitySG.infra.config.JwtTokenProvider;
import com.example.securitySG.models.RoleEntity;
import com.example.securitySG.models.UserEntity;
import com.example.securitySG.repostories.RoleRepository;
import com.example.securitySG.repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;

    }

    public RegisterUserDto createUser(RegisterUserDto registerUserDto) {
        // Verificar se o e-mail já existe
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new IllegalArgumentException("Username já existe no banco de dados.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerUserDto.getPassword()));
        user.setName(registerUserDto.getName());

        Set<RoleEntity> roles = registerUserDto.getRoles().stream().map(r -> new RoleEntity(r.name())).collect(Collectors.toSet());
        roleRepository.saveAll(roles);

        user.setRoles(roles);
        userRepository.save(user);
        return registerUserDto;
    }

    public UserResponseDto getDetails() {
        JwtServiceImpl jwtService = new JwtServiceImpl();
        String userName = jwtService.getUsername();
        UserEntity user = userRepository.findByUsername(userName).orElseThrow(() ->
                new UsernameNotFoundException("User not exists by Username or Email"));
        String message = String.format("Bem-vindo, %s!", user.getName());
        String department = jwtService.getDepartment();
        return new UserResponseDto(message, department);
    }

}

