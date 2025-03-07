package com.example.securitySG.controllers;

import com.example.securitySG.dtos.LoginDto;
import com.example.securitySG.dtos.UserDto;
import com.example.securitySG.models.UserEntity;
import com.example.securitySG.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
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

    // Tratamento de exceções de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}


