package com.ecommerce.ecommerce.projects.employeeService.exception;

public class ResourceNotFoundException extends RuntimeException {
    public  ResourceNotFoundException(String messsage) {
        super(messsage);
    }
}
