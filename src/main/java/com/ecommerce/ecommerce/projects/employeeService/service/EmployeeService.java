package com.ecommerce.ecommerce.projects.employeeService.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.projects.employeeService.dto.EmployeeDto;
import com.ecommerce.ecommerce.projects.employeeService.entity.Employee;
import com.ecommerce.ecommerce.projects.employeeService.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce.projects.employeeService.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;


@Service 
@RequiredArgsConstructor 
public class EmployeeService {
    private  final EmployeeRepository employeeRepository;
    private  final ModelMapper modelMapper;

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id) );
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
