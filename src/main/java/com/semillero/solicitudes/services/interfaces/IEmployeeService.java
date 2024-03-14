package com.semillero.solicitudes.services.interfaces;

import com.semillero.solicitudes.persistence.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {

    public List<EmployeeDto> getEmployees();

    public EmployeeDto getEmployeeById(Long employeeId);

    public EmployeeDto createEmployee(EmployeeDto employee);

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employee);

    public Boolean deleteEmployee(Long id);

}
