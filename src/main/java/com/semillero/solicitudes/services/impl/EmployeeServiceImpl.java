package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceNotComplete;
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
        verifyEmployeeExistence(employeeId);
        EmployeeEntity employee = this.employeeRepository.findById(employeeId).get();
        return this.employeeMapper.employeeToEmployeeDto(employee);

    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employee) {
            verifyEmployeeData(employee);
            EmployeeEntity employeeEntity = this.employeeMapper.employeeToEmployeeEntity(employee);
            EmployeeEntity employeeRepo = this.employeeRepository.save(employeeEntity);
            return this.employeeMapper.employeeToEmployeeDto(employeeRepo);

    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employee) {
        return null;
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        return null;
    }

    public void verifyEmployeeData(EmployeeDto employeeDto){
        if(employeeDto.getDsDocument()==null
        || employeeDto.getDsDocumentType()==null || employeeDto.getDsName()==null
        || employeeDto.getDsLastname() == null || employeeDto.getDsPhoneNumber() == null
        || employeeDto.getDsAddress() == null || employeeDto.getFeHireDate() == null
        || employeeDto.getDsTypeOfContract() == null || employeeDto.getDsEmployeeStatus() == null){
            throw new ResourceNotComplete("Employee data is not complete");
        }
    }

    public void verifyEmployeeExistence(Long id){
        if(!this.employeeRepository.existsById(id)){
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
    }
}
