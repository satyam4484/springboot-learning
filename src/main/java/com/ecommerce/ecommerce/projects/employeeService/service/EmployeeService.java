package com.ecommerce.ecommerce.projects.employeeService.service;

import java.util.List;

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
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .toList();
    }

    public EmployeeDto createNewEmployee(EmployeeDto employeeDto) {
        Employee newEmployee = modelMapper.map(employeeDto, Employee.class);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        return modelMapper.map(savedEmployee, EmployeeDto.class);

    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id: " + id));

        employee.setEmail(employeeDto.getEmail());
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);

        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
