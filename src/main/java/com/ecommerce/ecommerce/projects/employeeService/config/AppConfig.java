package com.ecommerce.ecommerce.projects.employeeService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ecommerce.ecommerce.projects.employeeService.dto.EmployeeDto;
import com.ecommerce.ecommerce.projects.employeeService.entity.Employee;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(EmployeeDto.class, Employee.class)
                .addMappings(mapper ->
                        mapper.skip(Employee::setId)
                );

        return modelMapper;
    }
    
}
