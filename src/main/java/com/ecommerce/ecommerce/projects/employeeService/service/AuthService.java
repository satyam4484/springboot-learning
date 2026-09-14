package com.ecommerce.ecommerce.projects.employeeService.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.projects.employeeService.dto.LoginRequest;
import com.ecommerce.ecommerce.projects.employeeService.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                );

        authenticationManager.authenticate(authenticationToken);

        String token = jwtService.generateToken(request.username());

        return new LoginResponse(token);
    }
}
