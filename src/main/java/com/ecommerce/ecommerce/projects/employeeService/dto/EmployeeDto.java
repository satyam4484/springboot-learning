package com.ecommerce.ecommerce.projects.employeeService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class EmployeeDto {
    private Long id;
    private  String email;
    private String name;
    private Long salary;
}
