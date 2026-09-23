package com.ecom.ecommerce.dto;

import java.time.LocalDateTime;

import com.ecom.ecommerce.constant.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private ErrorCode errorCode;
    private String path;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(
            String message,
            String path,
            T data) {

        return new ApiResponse<>(
                true,
                message,
                null,
                path,
                data,
                LocalDateTime.now()
        );
    }

    public static <T> ApiResponse<T> failure(
            String message,
            ErrorCode errorCode,
            String path) {

        return new ApiResponse<>(
                false,
                message,
                errorCode,
                path,
                null,
                LocalDateTime.now()
        );
    }
}