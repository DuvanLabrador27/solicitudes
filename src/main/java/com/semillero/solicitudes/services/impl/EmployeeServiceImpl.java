package com.semillero.solicitudes.services.impl;

import com.semillero.solicitudes.exceptions.ResourceBadRequestException;
import com.semillero.solicitudes.exceptions.ResourceNotFoundException;
import com.semillero.solicitudes.persistence.dto.EmployeeDto;
import com.semillero.solicitudes.persistence.entities.EmployeeEntity;
import com.semillero.solicitudes.persistence.mappers.EmployeeMapper;
import com.semillero.solicitudes.persistence.repositories.EmployeeRepository;
import com.semillero.solicitudes.services.interfaces.IEmployeeService;
import com.semillero.solicitudes.utils.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDto> getEmployees() {
        List<EmployeeEntity> users = employeeRepository.findAll();
        if(users.isEmpty()){
            throw new ResourceNotFoundException(Constants.LIST_NOT_FOUND_MESSAGE);
        }
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
        verifyDate(employee);
        EmployeeEntity employeeEntity = this.employeeMapper.employeeToEmployeeEntity(employee);
        EmployeeEntity employeeRepo = this.employeeRepository.save(employeeEntity);
        return this.employeeMapper.employeeToEmployeeDto(employeeRepo);
    }

    @Transactional
    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employee) {
        verifyEmployeeExistence(employeeId);
        verifyDate(employee);
        verifyEmployeeData(employee);
        EmployeeEntity employeeEntity = this.employeeRepository.findById(employeeId).get();
        employeeEntity.setDsDocument(employee.getDsDocument());
        employeeEntity.setDsDocumentType(employee.getDsDocumentType());
        employeeEntity.setDsName(employee.getDsName());
        employeeEntity.setDsLastname(employee.getDsLastname());
        employeeEntity.setDsPhoneNumber(employee.getDsPhoneNumber());
        employeeEntity.setDsAddress(employee.getDsAddress());
        employeeEntity.setFeHireDate(employee.getFeHireDate());
        employeeEntity.setFeDepartureDate(employee.getFeDepartureDate());
        employeeEntity.setDsTypeOfContract(employee.getDsTypeOfContract());
        employeeEntity.setDsEmployeeStatus(employee.getDsEmployeeStatus());
        EmployeeEntity employeeRepo = this.employeeRepository.save(employeeEntity);
        return this.employeeMapper.employeeToEmployeeDto(employeeRepo);
    }

    @Override
    public Boolean deleteEmployee(Long id) {
        verifyEmployeeExistence(id);
        EmployeeEntity employeeEntity = this.employeeRepository.findById(id).get();
        if(employeeEntity != null){
            this.employeeRepository.delete(employeeEntity);
            return true;
        }else{
            return false;
        }
    }

    public void verifyEmployeeData(EmployeeDto employeeDto) {
        if (employeeDto.getDsDocument() == null
                || employeeDto.getDsDocumentType() == null || employeeDto.getDsName() == null
                || employeeDto.getDsLastname() == null || employeeDto.getDsPhoneNumber() == null
                || employeeDto.getDsAddress() == null || employeeDto.getFeHireDate() == null
                || employeeDto.getDsTypeOfContract() == null || employeeDto.getDsEmployeeStatus() == null) {
            throw new ResourceBadRequestException("Employee data is not complete");
        }
    }

    public void verifyEmployeeExistence(Long id) {
        if (!this.employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException(Constants.EMPLOYEE_NOT_FOUND_MESSAGE + id);
        }
    }

    public void verifyDate(EmployeeDto employeeDto) {
        if (employeeDto.getFeDepartureDate() != null && employeeDto.getFeDepartureDate().isBefore(employeeDto.getFeHireDate())) {
            throw new ResourceBadRequestException("Departure date " + employeeDto.getFeDepartureDate()
                    + " can't be before hire date " + employeeDto.getFeHireDate());
        }
    }

}

