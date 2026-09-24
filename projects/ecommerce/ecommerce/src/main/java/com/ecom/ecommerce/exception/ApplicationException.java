package com.ecom.ecommerce.exception;

import com.ecom.ecommerce.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    public ApplicationException(
            String message,
            ErrorCode errorCode,
            HttpStatus status) {

        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}