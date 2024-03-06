package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.persistence.repositories.EmployeeRepository;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> getEmployees() {
        List<EmployeeEntity> users = employeeRepository.findAll();
        return users.stream().map(emp -> this.employeeMapper.employeeToEmployeeDto(emp)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        EmployeeEntity employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
        return this.employeeMapper.employeeToEmployeeDto(employee);

    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employee) {
        return null;
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {
        return null;
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        return null;
    }
}
