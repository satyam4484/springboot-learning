package com.ecommerce.ecommerce.projects.employeeService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.projects.employeeService.dto.EmployeeDto;
import com.ecommerce.ecommerce.projects.employeeService.service.EmployeeService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping ("/employees")
@RequiredArgsConstructor 
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById (@PathVariable Long id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);
        return  ResponseEntity.ok(employeeDto);
    }
    
    
}
