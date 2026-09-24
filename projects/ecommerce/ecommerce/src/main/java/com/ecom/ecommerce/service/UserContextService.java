package com.ecom.ecommerce.service;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.entity.User;
import com.ecom.ecommerce.exception.ApplicationException;
import com.ecom.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserContextService {

    private final UserRepository userRepository;

    public User getLoggedInUser(Authentication authentication) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApplicationException(
                                "Logged in user not found",
                                ErrorCode.USER_NOT_FOUND,
                                HttpStatus.NOT_FOUND
                        ));
    }
}