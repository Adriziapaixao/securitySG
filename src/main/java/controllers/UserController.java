package controllers;

import dtos.LoginDto;
import dtos.UserDto;
import lombok.AllArgsConstructor;
import models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.UserService;


import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserDetails (@PathVariable Long id) {
        Optional<UserEntity> user = userService.getDetails(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}


