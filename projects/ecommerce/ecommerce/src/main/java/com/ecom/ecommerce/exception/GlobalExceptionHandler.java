package com.ecom.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecom.ecommerce.constant.ErrorCode;
import com.ecom.ecommerce.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleProductNotFoundException(ProductNotFoundException ex,
            HttpServletRequest request) {
        ApiResponse<Void> response = ApiResponse.failure(
                ex.getMessage(),
                ErrorCode.PRODUCT_NOT_FOUND,
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(
            AuthenticationException exception,
            HttpServletRequest request) {

        ApiResponse<Void> response = ApiResponse.failure(
                "Invalid email or password",
                ErrorCode.UNAUTHORIZED,
                request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }
}
