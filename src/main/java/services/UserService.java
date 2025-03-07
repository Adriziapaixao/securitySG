package services;

import dtos.LoginDto;
import dtos.UserDto;
import models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {

    Optional<UserEntity> getDetails(Long userId);

    Map<String, String> login(LoginDto loginDto);

    UserDto createUser(UserDto userDto);
}
