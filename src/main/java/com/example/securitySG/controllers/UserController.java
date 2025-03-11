package com.example.securitySG.controllers;

import com.example.securitySG.controllers.dtos.LoginDto;
import com.example.securitySG.controllers.dtos.RegisterUserDto;
import com.example.securitySG.controllers.dtos.UserResponseDto;
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


    @PostMapping("/user")
    public RegisterUserDto createUser(@RequestBody RegisterUserDto registerUserDto){
        return userService.createUser(registerUserDto);
    }

    @GetMapping
    public UserResponseDto getDetails() {
        return userService.getDetails();
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


