package services;


import dtos.LoginDto;
import dtos.UserDto;
import models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repostories.UserRepository;

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
        return Map.of();
    }
}

