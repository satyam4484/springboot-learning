package com.ecommerce.ecommerce.projects.employeeService.controller;

import com.ecommerce.ecommerce.projects.employeeService.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.projects.employeeService.dto.LoginRequest;
import com.ecommerce.ecommerce.projects.employeeService.dto.LoginResponse;
import com.ecommerce.ecommerce.projects.employeeService.dto.RegisterRequest;
import com.ecommerce.ecommerce.projects.employeeService.entity.User;
import com.ecommerce.ecommerce.projects.employeeService.repository.UserRepository;

@RestController 
@RequiredArgsConstructor 
@RequestMapping ("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
   

    @PostMapping ("/register")
    public String register(@RequestBody RegisterRequest request) {

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(
            passwordEncoder.encode(request.password())
        );
        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}